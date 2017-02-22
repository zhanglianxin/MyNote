package lianxin;

import org.junit.Test;

/**
 * [冒泡排序 两次优化](http://www.2cto.com/kf/201303/194821.html "冒泡排序")
 * 
 * @author zhanglianxin
 *
 */
public class BubbleSortTest {

    @Test
    public void test() {
        int[] array = { 6, 5, 7, 1, 8, 4, 3, 2, };
//        bubbleSort0(array);
        bubbleSort1(array);
//        bubbleSort2(array);
        for (int i : array) {
            System.out.print(i + " ");
        }
    }

    public static void bubbleSort0(int[] array) {
        // i 可以理解为第 i 趟排序，也可以理解为从末尾起此次排序后已经有序的元素个数
        for (int i = 1; i < array.length; i++) {
            // j 为当前比较的元素的索引
            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
    }

    public static void bubbleSort1(int[] array) {
        // i 可以理解为第 i 趟排序，也可以理解为从末尾起此次排序后已经有序的元素个数
        for (int i = 1; i < array.length; i++) {
            boolean exchanged = false;
            // j 为当前比较的元素的索引
            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    exchanged = true;
                }
            }
            // 一趟比较之后，发现该数组是有序的，则结束循环
            if (!exchanged) {
                break;
            }
        }
    }

    public static void bubbleSort2(int[] array) {
        int index = 0;
        int mark = array.length - 1;
        // i 可以理解为第 i 趟排序，也可以理解为从末尾起此次排序后已经有序的元素个数
        for (int i = 1; i < array.length; i++) {
            boolean exchanged = false;
            // j 为当前比较的元素的索引
            for (int j = 0; j < mark; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    exchanged = true;
                    index = j; // 记住最后一次比较的元素的索引，此后的元素都是有序的
                }
            }
            // 一趟比较之后，发现该数组是有序的，则结束循环
            if (!exchanged) {
                break;
            }
            mark = index; // 修改需要遍历到的元素的索引
        }
    }
}
