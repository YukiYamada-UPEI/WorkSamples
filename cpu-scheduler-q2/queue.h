#ifndef QUEUE
#define QUEUE

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
	int MAX;
	int head;
	int tail;
	int shortest;
	int size;
	int type; //cpu=0, io=1
	my_process* queue[100];
}my_queue;

my_queue* add_queue(my_queue *q, my_process *p);
my_process *remove_queue(my_queue *q);
my_process *remove_shortest(my_queue *q);
void update_shortest(my_queue *q);
void initialize_queue(my_queue *q, int type);
void print_queue(my_queue *q);
int isempty(my_queue* q);



#endif
