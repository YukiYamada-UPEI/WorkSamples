
#include "scheduler.h"

RESULT *result;

void start(holder* pholder, processor* cpu, processor* io, my_queue* cpu_queue, my_queue* io_queue){
	int end = 0;
	int n = pholder->n;
	int on = 0;
	
	
	while(end < n){
		if(on) step_time(1, cpu, io);
		on = 1;
		
		//sleep(0.1);
		//print_all(cpu, io, cpu_queue, io_queue);
		
		//Arrived -> CPU queue
		put_arrived(pholder, cpu_queue);

		//CPU -> IO queue
		if(is_filled(cpu))
			if(is_finished(cpu)){
				my_process* p = remove_p(cpu);
				
				//process finished
				if(isdone(p)){
					finish(p, result->totaltime);
					put_finished(result, p);
					end++;
				}
				//process still has bursts
				else
					add_queue(io_queue, p);
			}
		
		//IO -> CPU queue
		if(is_filled(io))
			if(is_finished(io)){
				add_queue(cpu_queue, remove_p(io));
			}

		//CPU queue -> CPU
		if(!is_filled(cpu))
			if(!isempty(cpu_queue))
			add_p(cpu, remove_shortest(cpu_queue));

		//IO queue -> IO
		if(!is_filled(io))
			if(!isempty(io_queue))
			add_p(io, remove_shortest(io_queue));	

		
	}

	/*print results*/
	print_pinfos(result);
	printf("%s\n%s\n\n", cpu->flow, io->flow);
	print_result(result);

	
}

int main(){
	
	/*Result struct*/
	result = (RESULT*)malloc(sizeof(RESULT));
	reset_result(result);

	/*process holder before arrival*/
	holder *pholder = (holder*)malloc(sizeof(holder));
	
	/*open file*/
	open_file_create(choosefile(), pholder);
	
	//print the processes
	printf("\n");
	int j;
	for(j=0;j<pholder->n;j++){
		printp(pholder->array[j]);
	}
	printf("\n");
	
	/*Prepare queues*/
	my_queue *cpu_queue = (my_queue*)malloc(sizeof(my_queue));
	my_queue *io_queue = (my_queue*)malloc(sizeof(my_queue));
	
	initialize_queue(cpu_queue, 0);
	initialize_queue(io_queue, 1);
	
	/*Prepare processors*/
	processor *cpu = (processor*)malloc(sizeof(processor));
	processor *io = (processor*)malloc(sizeof(processor));
	
	initialize_processor(cpu, 0);
	initialize_processor(io, 1);
	
	start(pholder, cpu, io, cpu_queue, io_queue);
	
	//free pointers
	free(cpu_queue);
	free(io_queue);
	free(cpu);
	free(io);
	free(result);
	free(pholder);
	
	
	/*Finish*/
	printf("<<Enter any letter to terminate>>\n");
	char e[10] = "";
	scanf("%s", e);
	printf("bye\n");
	sleep(1);


	return 0;
}



void print_all(processor* cpu, processor* io, my_queue* cpu_q, my_queue* io_q){
	printf("\n");
	print_processor(cpu);
	print_processor(io);
	print_queue(cpu_q);
	print_queue(io_q);
}

void step_time(int t, processor* cpu, processor* io){
	result -> totaltime += t;
	step_u_time(cpu, t);
	step_u_time(io, t);
}

void put_arrived(holder* h, my_queue *q){
		while( (h->i < h->n) &&((h->array[h->i])->arrival <= result->totaltime) ){
			add_queue(q, h->array[h->i]);
			h->i++;
		}
}

char* choosefile(){
	char num = ' ';
	printf("There are four input files\nEnter a number you like. (1 2 3 4 5):");
	scanf("%c", &num);
	switch(num){
		case '1': return "process.txt";
		case '2': return "process2.txt";
		case '3': return "process3.txt";
		case '4': return "process4.txt";
		case '5': return "process5.txt";
		default : return "process0.txt";
	}
}


int open_file_create(char fname[], holder* h){
	FILE *fp;

	printf("openfile is called\n");
	
	if( (fp=fopen(fname, "r")) == NULL){
		printf("File open error\n");
		return -1;
	} 
	
	char s[256];
	while(fgets(s, sizeof(s), fp) != NULL){
		my_process *pp = (my_process*)malloc(sizeof(my_process));
		h->array[h->i] = create_process(s, pp);
		h->n++;
		h->i++;
	}
	h->i=0;
	return 0;
}
