#ifndef PROCESSOR
#define PROCESSOR

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/shm.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <time.h>
#include <pthread.h>


#include "myprocess.h"

typedef struct {
	my_process *current;
	int totaltime;
	int time;
	int filled; //0 empty, 1 filled
	int type; //0 CPU, 1 IO
	char flow[1000000];
}processor;

/*thread functions*/
void run_processor(pthread_t tid, pthread_attr_t attr);
void *process_runner(void *param); /*UPU thread*/

int is_filled(processor* u);
void add_p(processor* u, my_process* p);
my_process* remove_p(processor* u);
int is_finished(processor* u);
void initialize_processor(processor* u, int type);
void increase_u_time(processor* u);
void step_u_time(processor* u, int t);
void print_processor(processor *u);
void record(processor* u);
void record_wait(processor*u);






#endif
