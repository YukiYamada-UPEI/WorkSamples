#ifndef _SCHEDULER_
#define _SCHEDULER_

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <malloc.h>
#include <sys/stat.h>
#include <unistd.h>
#include <errno.h>
#include <stdbool.h>

#include "processor.h"
#include "queue.h"
#include "myprocess.h"
#include "result.h"
#include "data.h"




//Just temporary array to virtualize arrival times
char process_lines[10][256];
my_process process_holder[10]; 
//RESULT *result;
int arrival_index = 0;


/*functions*/
int check_arrival();
void start();
void increment_time();
void step_time(int t, processor* cpu, processor* io);
void initialize_result();
void printp(my_process *P1);
int open_file_create(char fname[], holder* h);
void put_arrived(holder* h,  my_queue *q);
void print_all(processor* cpu, processor* io, my_queue* cpu_q, my_queue* io_q);
char* choosefile();



#endif
