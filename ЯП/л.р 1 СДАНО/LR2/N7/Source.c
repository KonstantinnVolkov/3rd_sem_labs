#include <stdio.h>
#include <locale.h>
#include <time.h>

void automatic(int arr[6][6]) {
	printf("Сгенерированный массив\n");
	for (int i = 0; i < 6; i++)
	{
		for (int j = 0; j < 6; j++) 
		{
			arr[i][j] = rand() %7 +2;
			printf("%d ", arr[i][j]);
		}
		printf("\n");
	}
}

void byHands(int arr[6][6]) {
	printf("Вводите массив:\n");
	for (int i = 0; i < 6; i++)
	{
		for (int j = 0; j < 6; j++)
		{
			scanf_s("%d", &arr[i][j]);
		}
		printf("\n");
	}
	printf("Введенный массив:\n");
	for (int i = 0; i < 6; i++)
	{
		for (int j = 0; j < 6; j++)
		{
			printf("%d ", arr[i][j]);
		}
		printf("\n");
	}

}

int main() {
	setlocale(LC_ALL, "Rus");
	srand(time(NULL));
	int arr[6][6];
	int max = 0;
	int inputWay;
	printf("Выберите способ инициализации матрицы\n");
	printf("1: Автоматически\n");
	printf("2: Вруную\n");
	do {
		scanf_s("%d", &inputWay);
		if ((inputWay == 1) || (inputWay == 2))
		{
			break;
		}
		else {
			printf("Неверная команда\n");
		}
	} while (1);
	switch (inputWay)
	{
	case 1:
		automatic(arr);
		break;
	case 2:
		byHands(arr);
		break;
	}
	for (int i = 0; i < 6; i++)
	{	
		for (int j = 0; j < 6; j++)
		{
			if (arr[i][j] > max) {
				max = arr[i][j];
			}

		}
	}

	printf("Максимальный элемент: %d", max);
	printf("\n");
	printf("Индексы:\n");

	for (int i = 0; i < 6; i++)
	{
		for (int j = 0; j < 6; j++)
		{
			if (arr[i][j] == max) {
				printf("[%d][%d]\n",i,j);
			}

		}
	}
	_getch();
	return 0;
}