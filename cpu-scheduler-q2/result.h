#ifndef _RESULT_
#define _RESULT_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/shm.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <time.h>
#include "myprocess.h"


typedef struct{
	int totaltime;
	int n; //number of process

	double ave_turnaround;     //for the system
	double ave_wait;          //for the system
	double throughput;	       //for the system
	
	my_process* array[100];
}RESULT;

void reset_result(RESULT *result);
void put_finished(RESULT *result, my_process *p);
void calc_result(RESULT *result);
void print_result(RESULT *result);
void print_pinfos(RESULT *result);




#endif
