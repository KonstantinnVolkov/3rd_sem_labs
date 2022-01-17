#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <windows.h>

#define CRT_SECURE_NO_WARNINGS

//№24 Поменять местами минимальный и первый элементы очереди

struct nodeStruct
{
	int data;
	int min;
	struct nodeStruct* next;
};
typedef struct nodeStruct node;

struct queueStruct
{
	node* front;
	node* rear;
};
typedef struct queueStruct queue;

bool isEmpty(queue* q);
void enqueue(queue* q, int data);
int dequeue(queue* q, int queueLength);
void showQueue(queue* q, int queueLength);
void swapMinAndFirst(queue* q, int queueLength);

void main() {
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	queue* q;
	q = malloc(sizeof(queue));
	q->front = NULL;
	q->rear = NULL;
	node* head = NULL;
	node* tail = NULL;
	int value = 0, k;

	printf("Введите число для помещения в очередь:\n");
	scanf("%d", &value);
	do {
		if (value > 50 || value < -50)
		{
			printf("Число должно быть в интервале [-50;50]\n");
			printf("Введите число для помещения в очередь:\n");
			scanf("%d", &value);
		}
	} while (value > 50 || value < -50);
	enqueue(q, value);
	int queueLength = 1;
	tail = head;

	while (true)
	{
		printf("Menu:\n 1-enqueue;\n 2-dequeue;\n 3-swap;\n 4-view;\n 5-queue length;\n");
		scanf("%d", &k);

		switch (k)
		{
		case 1:
			printf("Enter number:\n");
			scanf("%d", &value);
			enqueue(q, value);
			queueLength++;
			break;
		case 2:
			queueLength = dequeue(q, queueLength);
			break;
		case 3:
			swapMinAndFirst(q, queueLength);
			break;
		case 4:
			printf("Queue content:\n");
			showQueue(q, queueLength);
			break;
		case 5:
			printf("Queue length: %d\n", queueLength);
		}
	}

}

bool isEmpty(queue* q)
{
	return (q->rear == NULL);
}

void enqueue(queue* q, int value) {
	node* tmp;
	tmp = (node*)malloc(sizeof(node));
	tmp->data = value;
	tmp->next = NULL;
	if (isEmpty(q))
	{
		tmp->min = value;
		q->front = q->rear = tmp;
	}
	else if (value < q->rear->min)
	{
		tmp->min = value;
		q->rear->next = tmp;
		q->rear = tmp;
	}
	else {
		tmp->min = q->rear->min;
		q->rear->next = tmp;
		q->rear = tmp;
	}
}

int dequeue(queue* q, int queueLength) {
	node* tmp;
	tmp = q->front;
	if (queueLength > 0)
	{
		q->front = q->front->next;
		free(tmp);
		queueLength--;
		return queueLength;
	}
	else
	{
		printf("Удалять нечего, очередь пуста\n");
		return queueLength;
	}
}

void showQueue(queue* q, int queueLength) {
	node* tmp;
	tmp = q->front;
	if (queueLength > 0)
	{
		while (tmp != NULL)
		{
			printf("%d ", tmp->data);
			tmp = tmp->next;
		}
		printf("\n");
	}
	else
	{
		printf("Очередь пуста\n");
	}
	
}

void swapMinAndFirst(queue* q, int queueLength) {
	int counter = -1;
	node* tmp;
	tmp = (node*)malloc(sizeof(node));
	node* tmp1; 
	tmp1 = (node*)malloc(sizeof(node));
	tmp = q->front;
	if (queueLength == 1 || queueLength == 0)
	{
		printf("Нечего менять\n");
		return;
	}
	else
	{
		while (tmp != NULL)
		{
			if (q->rear->min == tmp->min)
			{
				printf("Min element: %d\n", tmp->min);

				tmp1->data = q->front->data;
				tmp1->min = q->front->min;

				q->front->data = tmp->data;
				q->front->min = tmp->min;

				tmp->data = tmp1->data;
				tmp->min = tmp1->min;
				return;
			}
			else
			{
				tmp = tmp->next;
				counter++;
			}
		}
		printf("Элементов между первым и минимальным: %d\n", counter);
	}
}
