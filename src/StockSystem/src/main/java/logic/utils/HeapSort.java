package logic.utils;

import java.math.BigDecimal;
import java.util.ArrayList;

import vo.SortVO;

public class HeapSort {

	private static int heapSize;

	// 如果你需要调用排序方法，你需要自己构造排序类型，然后将作为排序依据，在数据层依据返回的ArrayList<Stockpo>
	// 构造ResultListPO,此方法为降序排列
	public static ArrayList<SortVO> heapSort(ArrayList<SortVO> a) {
		heapSize = a.size();
		buildMinHeap(a);
		for (int i = a.size() - 1; i >= 1; i--) {
			swap(a, i, 0);
			heapSize = heapSize - 1;
			minHeapify(a, 0);
		}

		return a;
	}

	// 此方法为升须排列
	public static ArrayList<SortVO> AntiHeapSort(ArrayList<SortVO> a) {
		a = heapSort(a);
		ArrayList<SortVO> antiSort = new ArrayList<SortVO>();
		for (int i = a.size() - 1; i >= 0; i--) {
			antiSort.add(a.get(i));
		}
		return antiSort;
	}

	public static void swap(ArrayList<SortVO> a, int i, int j) {
		// BigDecimal temp[] = a[i];
		// a[i] = a[j];
		// a[j] = temp;
		SortVO SortVO = a.get(i);
		a.set(i, a.get(j));
		a.set(j, SortVO);

	}

	private static void buildMinHeap(ArrayList<SortVO> a) {
		for (int i = a.size() / 2; i >= 0; i--) {
			minHeapify(a, i);
		}

	}

	private static void minHeapify(ArrayList<SortVO> a, int i) {

		int l = left(i);
		int r = right(i);
		int largest = i;
		if (l < heapSize && compare(a.get(l).getData(), a.get(i).getData()))
			largest = l;
		else
			largest = i;
		if (r < heapSize && compare(a.get(r).getData(), a.get(largest).getData()))
			largest = r;
		if (largest != i) {
			swap(a, i, largest);
			minHeapify(a, largest);
		}

	}

	private static int left(int i) {
		return 2 * i;
	}

	private static int right(int i) {
		return 2 * i + 1;
	}

	private static boolean compare(BigDecimal a, BigDecimal b) {
		if (a.compareTo(b) == -1)
			return true;// 如果a<b,返回真，否则返回false
		else
			return false;
	}

}
