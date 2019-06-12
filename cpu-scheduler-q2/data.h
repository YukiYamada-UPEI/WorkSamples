#ifndef DATA
#define DATA
#include "myprocess.h"

/*stores processes which have not reached arrival time*/
typedef struct{
	my_process* array[100];
	int i;
	int n;
}holder;

#endif
