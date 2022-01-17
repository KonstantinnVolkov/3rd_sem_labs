#include <stdio.h>
#include <locale.h>
#include <time.h>
#include <malloc.h>

void showMatrix(int arr[50][50], int n, int m)
{
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < m; j++)
		{
			printf("%d ", arr[i][j]);
		}
		printf("\n");
	}
}

void main() {
	setlocale(LC_ALL, "Rus");
	srand(time(NULL));
	printf("Введите размерность матрицы NxM\n");
	int n, m;
	int arr[50][50];

	printf("N:\n");
	scanf_s("%d", &n);
	printf("M:\n");
	scanf_s("%d", &m);
	
	int fillChoise = 0;
	do
	{
		printf("Выберите способ заполнения матрицы:\n1 - автоматически\n2 - вручную\n");
		scanf_s("%d", &fillChoise);
	} while (fillChoise < 1 || fillChoise>2);

	switch (fillChoise)
	{

	case 1:
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < m; j++)
			{
				arr[i][j] = 2 + (rand() % (20 - 2 + 1));
			}
		}
		break;

	case 2:
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < m; j++)
			{
				do
				{
					printf("arr[%d][%d] = ", i, j);
					scanf_s("%d",(&arr[i][j]));
					if (arr[i][j] == 1 || arr[i][j] == 0)
						printf("Матрица не должна содержать нулей и единиц\n");
				} while (arr[i][j] == 1 || arr[i][j] == 0);
			}
		}
		break;
	}

	printf("Полученная матрица:\n");
	showMatrix(arr, n, m);
	int counter = 0;

	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < m; j++)
		{
			if (arr[i][j] == 1 || arr[i][j] == 0)
				continue;
			char founded = 0;
			for (int k = i; k < n; k++)
			{
				for (int z = 0; z < m; z++)
				{
					if (z <= j && i == k)
						continue;
					if (arr[i][j] == arr[k][z])
					{
						arr[k][z] = 1;
						founded = 1;
						counter++;
					}
				}
			}
			if (founded) {
			arr[i][j] = 1;
			}
			else
				arr[i][j] = 0;
		}
	}

	printf("Преобразованная матрица:\n");
	showMatrix(arr, n, m);

	for (int i = 0; i < n; i++)
	{
		int zero = 0, one = 0;
		for (int j = 0; j < m; j++)
		{
			if (arr[i][j] == 0)
				zero++;
			else
				one++;
		}
		printf("Строка %d - Количество нулей: %d, количество единиц: %d \n",i,zero,one); 
	}

	for (int i = 0; i < n; i++)
	{
		int zero = 0, one = 0;
		for (int j = 0; j < m; j++)
		{
			if (arr[j][i] == 0)
				zero++;
			else
				one++;
		}
		printf("Столбец %d - Количество нулей: %d, количество единиц: %d \n", i, zero, one);
	}
	printf("Кол-во повторов: %d",counter);
}