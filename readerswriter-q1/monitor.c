#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#include <sys/shm.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <time.h>
#include <pthread.h>



typedef struct{
	int datalength;
	char info[100];
}INFO;

typedef struct{
	int MAX;
	int size;
	int head;
	int tail;
	pid_t arr[30];
	char rw[30];
	int count;
	
	int n_readers;
	int waiting_readers;
	int n_writers;
	int waiting_writers;
	bool can_read;
	bool can_write;
}monitor_queue;

const int QUEUE_MAX = 30;

void reset_queue(monitor_queue* q);
void print_queue(monitor_queue* q);
void put_yourself(monitor_queue* q, pid_t id, char type);
pid_t dispatch_next(monitor_queue* q);
void finishing();
int suspend(int pid);
int wakeup(int pid);
void write_info(INFO* info_ptr, char* info);
char* read_info(INFO* info_ptr);
void write_random(INFO* info_ptr);

int try_write(INFO* info, monitor_queue* q);
int try_read(INFO* info, monitor_queue* q);

void start_writer(INFO* info_ptr, monitor_queue* q);
void start_reader(INFO* info_ptr, monitor_queue* q);

void *runner_monitor(void *param);

void begin_write(monitor_queue *q);
void begin_read(monitor_queue *q);
void end_write(monitor_queue *q);
void end_read(monitor_queue *q);

void wait_can(char rw, monitor_queue *q);
void signal_can(char rw, monitor_queue *q);



int main(){
		
	/* the identifier for the shared memory segment */
	int segment_id;
	int segment_id2;
	
	/*  a pointer to the shared memory segment */
	INFO* info_ptr;
	monitor_queue* queue_ptr;

	/* allocate a shared memory segment*/
	segment_id=shmget(IPC_PRIVATE, sizeof(INFO), S_IRUSR| S_IWUSR);
	segment_id2=shmget(IPC_PRIVATE, sizeof(monitor_queue), S_IRUSR| S_IWUSR);

	printf("Info  Segment Id = %d\n",segment_id);
	printf("Queue Segment Id = %d\n",segment_id2);

	/* attach the shared memory segment */
	info_ptr =(INFO*)shmat(segment_id, NULL,0);	
	queue_ptr=(monitor_queue*)shmat(segment_id2, NULL,0);
	


	int nchild = 10; //number of reader + writer children
	queue_ptr -> count = 0;
	printf("COUNT = %d\n", queue_ptr -> count);

	/*Thread to sum up results*/
	pthread_t tid;
	pthread_attr_t attr; 
	pthread_attr_init(&attr);
	pthread_create(&tid ,&attr ,runner_monitor,(void*)queue_ptr); 
	

	sprintf(info_ptr->info,"%s","NONE");
	info_ptr->datalength = strlen("NONE");

	printf("datalength=%d\n", info_ptr->datalength);
	printf("info:%s\n", info_ptr->info);
	
	reset_queue(queue_ptr);
	printf("max: %d\n", queue_ptr->MAX);
	srand(time(NULL));
	
	int i = 0;
	int r;
	while(i<nchild){
		r = rand()%nchild;
		if(fork()==0){
			if(r < 5){
				start_writer(info_ptr, queue_ptr);
				printf("Writer(%d) finished\n", getpid());
				queue_ptr->count++;
				exit(0);
			}
			else if(r < 10){
				start_reader(info_ptr, queue_ptr);
				printf("Reader(%d) finished\n", getpid());
				queue_ptr->count++;
				exit(0);
			}
		}
		i++;
	}

	
	while(queue_ptr->count<10){

	}

	/* remove the shared memory segment*/
	shmdt(info_ptr);
	shmctl(segment_id,IPC_RMID,NULL);
	sleep(2);
	finishing();
	return 0;
}


void start_writer(INFO* info_ptr, monitor_queue* q){

	int lives = 3;
	while(lives>0){
		sleep(random()%2+1);
		if(try_write(info_ptr, q)){
			lives--;
		}

		put_yourself(q, getpid(), 'W');
		suspend(getpid());
		
	}

}


void start_reader(INFO* info_ptr, monitor_queue* q){
	int lives = 3;
	while(lives>0){
		sleep(random()%3+3);
		if(try_read(info_ptr, q)){
			lives--;
		}

		put_yourself(q, getpid(),'R');
		suspend(getpid());
	}
}

int try_write(INFO* info, monitor_queue* q){
	begin_write(q);
	write_random(info);
	end_write(q);
	return 1;
}
int try_read(INFO* info, monitor_queue* q){
	begin_read(q);
	read_info(info);
	end_read(q);
	return 1;
}

void wait_can(char rw, monitor_queue *q){
	if(rw=='r') {while(!q->can_read); return;};
	if(rw=='w') {while(!q->can_write); return;};
}

void signal_can(char rw, monitor_queue *q){
	if(rw=='r') {q->can_read = !q->can_read; return;};
	if(rw=='w') {q->can_write = !q->can_write; return;};
}

void begin_write(monitor_queue *q){
	if(q->n_writers == 1 || q->n_readers > 0){
		++q->waiting_writers;
		wait_can('w',q);
		--q->waiting_writers;
	}
	q->n_writers = 1;
}

void begin_read(monitor_queue *q){
	if(q->n_writers==1 || q->waiting_writers > 0){
		++q->waiting_readers;
		wait_can('r',q);
		--q->waiting_readers;
	}
	++q->n_readers;
	signal_can('r', q);
}

void end_write(monitor_queue *q){
	q->n_writers = 0;
	if(q->waiting_readers)
		signal_can('r', q);
	else
		signal_can('w', q);
}

void end_read(monitor_queue *q){
	if(--q->n_readers==0)
		signal_can('r', q);
}

char *words[11] ={"hello world", "book", "suger", "Japan", 
		"whachamacallit","surprised", "A", "UPEI","fly", "prolog" } ;


void write_random(INFO* info_ptr){
	write_info(info_ptr, words[rand()%11]);
}

void write_info(INFO* info_ptr, char* info){
	printf("   pid[%d] wrote info(%d)=\"%s\"\n", getpid(), strlen(info), info);
	info_ptr->datalength = strlen(info);
	sprintf(info_ptr->info,"%s",info);
}


char* read_info(INFO* info_ptr){
	printf("   pid[%d] read info(%d)=\"%s\"\n",getpid(),info_ptr->datalength, info_ptr->info);
	return info_ptr->info;
}


void put_yourself(monitor_queue* q, pid_t id, char type){
	if(q->size < q->MAX){
		q->arr[q->tail] = id;
		q->rw[q->tail] = type;
		q->tail = (q->tail+1) % q->MAX;
		q->size++;
		print_queue(q);
	}
	else printf("queue is full\n");
}

pid_t dispatch_next(monitor_queue* q){
	pid_t temp = -1;
	if(q->size > 0){
		temp = q->arr[q->head];
		q->arr[q->head] = (pid_t)0;
		q->rw[q->head] = 0x00;
		q->head = (q->head+1) % q->MAX;
		q->size--;
		print_queue(q);
	}
	else printf("queue is empty\n");
	return temp;
}

void print_queue(monitor_queue* q){
	int i = q->head;
	int t = q->tail;
	int gap = 0;
	printf("Queue(%2d) |", q->size);
	while(i != t){
		printf("%c|", q->rw[i]);
		i = (i+1) % q->MAX;
		gap++;
	}
	i=0;
	while(i< 15 - gap){
		printf(" |");
		i++;
	}
	printf("\n");
}

void reset_queue(monitor_queue* q){
	q->size = q->head = q->tail = 0;
	q->MAX = QUEUE_MAX;
	
	q->can_read = true;
	q->can_write= true;
}



void finishing(){
	char done[20] = "";
	char *line = "================================================\n";
	printf("%sEnter 'y' to terminate: ", line);
	scanf("%s", done);
	printf("%s\n", done);
}

int suspend(int pid){
   return kill(pid,SIGSTOP);
}

int wakeup(int pid){
  return kill(pid,SIGCONT);  
}

int lock_count = 0;

void *runner_monitor(void *param){
	monitor_queue* q = (monitor_queue*) param;
	while(q->count < 10){

		if(q->size >0){
			if(q->rw[q->head] == 'R'){
				if(q->n_writers == 0){
					wakeup(dispatch_next(q));
					lock_count = 0;
				}
				else{
					lock_count++;
				}
			}
			else if(q->rw[q->head] == 'W'){
				if(q->n_writers == 0 && q->n_readers==0){
					wakeup(dispatch_next(q));
					lock_count = 0;
				}
				else{
					lock_count++;
				}
			}
		}
		sleep(1);
		print_queue(q);
		if(lock_count > 5){
			q->n_readers = 0;
			q->n_writers = 0;
			q->waiting_readers = 0;
			q->waiting_writers = 0;
			q->can_write = true;
			q->can_read = true;
			lock_count=0;
		}
	}
	
	printf("\nEvery process terminated without conflicts:\n\n");
	pthread_exit(0);
}



