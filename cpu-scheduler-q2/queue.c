#include "queue.h"


void print_queue(my_queue *q){
	int h = q->head;
	int t = q->tail;
	int i = h;
	if(q->type==0) printf("   CPU");
	else printf("   I/O");
	printf(" queue size=%d |", q->size);
	while(i != t){
		printf("%d(%d)|",q->queue[i]->pid, q->queue[i]->burst[q->queue[i]->i]);
		i++;
	}
	printf("\n");
	
}

int isempty(my_queue* q){
	if(q->size > 0) return 0;
	return 1;
}

my_queue* add_queue(my_queue *q, my_process *p){

	if(q->size < q->MAX){
		q->queue[q->tail] = p; //adding
		q->tail = (q->tail+1) % q->MAX;
		q->size++;
		update_shortest(q);
	}
	return q;
}

my_process* remove_queuee(my_queue *q){
	my_process* temp;
	if(q->size > 0){
		temp = q->queue[q->head];
		q->head++;
		q->size--;
	}
	return temp;
}

my_process* remove_shortest(my_queue *q){

	my_process* next = q->queue[q->shortest];
	//printp(next);

	int s = q->shortest;

	while(s < q->tail){
		if(s==q->MAX-1){
			q->queue[q->MAX-1] = q->queue[0];
		} else {
			q->queue[s] = q->queue[s+1];
		}
		s = (s+1)%q->MAX;
	}

	q->tail = q->tail-1;
	update_shortest(q);	
	q->size--;
	
	//printf("address(f): %p\n", next);
	return next;
}

void update_shortest(my_queue *q){
	int min = 1000000;
	int i = q->head;
	int s = q->head;
	int t = q->tail;
	while(i != t){
		int key = q->queue[i]->burst[q->queue[i]->i];
		if(min > key){
			min = key;
			s = i;
		}
		i = (i+1)% (100);
	}
	q->shortest = s;
}

void initialize_queue(my_queue *q, int type){
	q->head = 0;
	q->tail = 0;
	q->size = 0;
	q->MAX = 100000;
	q->shortest = -1;
	q->type = type;
}


