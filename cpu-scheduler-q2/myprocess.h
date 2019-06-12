#ifndef MYPROCESS
#define MYPROCESS

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/shm.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <time.h>


/*process struct*/

typedef struct{
	int pid;
	int arrival;
	int finish;
	int turnaround;
	int waiting;
	int totalburst;

	int burst[100];
	int n;
	int i;
	int t; //cpu=0, io=1
	
}my_process;

//my_process* create_processs(char *line);
void printp(my_process*p);
int isdone(my_process* p);
void process_removed(my_process* p);
my_process* create_process(char *line, my_process *p);
void finish_msg(my_process* p);
void print_presult(my_process* p);
void print_pinfo(my_process* p);
void finish(my_process*p, int totaltime);







#endif
