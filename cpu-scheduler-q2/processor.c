#include "processor.h"

void print_processor(processor *u){
	if(u->type == 0) printf("   CPU ");
	else printf("   I/O ");;
	printf("ttime:%d, time:%d, filled:%d, finished:%d ", u->totaltime, u->time, is_filled(u), is_finished(u)); 
	if(u->filled)
		printf("[pid%d, burst=%d]\n",u->current->pid, u->current->burst[u->current->i]);
	else printf("[empty]\n");
}

int is_filled(processor* u){
	return u->filled;
}

void add_p(processor* u, my_process* p){
	record_wait(u);
	u->time = 0; //reset timer
	u->filled = 1;
	u->current = p;
}

my_process* remove_p(processor* u){
	record(u);
	process_removed(u->current);
	u->filled = 0;
	u->time = 0; //reset timer
	return u->current;
}

void record(processor* u){
	if(u->filled){
		char f[100];
		sprintf(f, "-> P%d(%d)",u->current->pid, u->current->burst[u->current->i]);		
		strcat(u->flow, f);
	}
}
void record_wait(processor*u){
	if(!(u->filled) && (u->time > 0) ){
		char f[100];
		sprintf(f, "-> W(%d)", u->time);	
		strcat(u->flow, f);
	}
}

int is_finished(processor* u){
	if(!u->filled) return 0;
	my_process *p = u->current;
	
	if(p->burst[p->i] <= u->time )
		return 1; //finished
	return 0; //not-finished
}

void increase_u_time(processor* u){
	u->time += 1;
	u->totaltime += 1;
}

void step_u_time(processor* u, int t){
	u->time += t;
	u->totaltime += t;
}


void initialize_processor(processor* u, int type){
	u->totaltime = 0;
	u->time = 0;
	u->filled = 0;
	u->type = type;

	if(type==0) strcpy(u->flow, "CPU: ");
	else strcpy(u->flow, "IO: ");
	//printf(" %s", u->flow);
}




