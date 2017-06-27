import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MyDlist extends DList{
	
	public MyDlist() {
		// TODO Auto-generated constructor stub
	}
	public MyDlist(String f) throws IOException {
		// override constructor to read txt file and instream
		super();
		Scanner sc = new Scanner(System.in);
		if (f.equals(("stdin"))){
			while (true){
                String line = sc.nextLine(); 
				if (line.equals("")){
					break;
				}
				addLast(new DNode(line,null,null));
				}
			}
		else{
			Scanner s = new Scanner(new FileInputStream(new File(f)));
			while(s.hasNext()){ 
				addLast(new DNode(s.next(),null,null));
				} 
			}
		}
		
	public static void printList(MyDlist u){
		//there time complexity should be O(n) cause the loop only goes n times
		DNode temper = u.header.next;
		while (temper.getElement()!=null){
			System.out.println(temper.getElement());	
			temper = temper.next;
		}
	}
	public static MyDlist cloneList(MyDlist u){
		//there time complexity should be O(n) cause the loop only do n times
		//to clone a list, we need to get the first element, and by this element to find the next
		MyDlist md2 = new MyDlist();
		DNode dtemp = u.header.next;
		//get the first element
		while(dtemp.getElement()!=null){
			md2.addLast(new DNode(dtemp.getElement(),null,null));
			//copy the node, and put into mydlist
			dtemp=dtemp.next;
		}
		return md2;
	}
	
	/* 1.To find the union and intersection, firstly I put all their values
	 * into two String arry, then use merge sort to sort them.
	 * 2.Then compare these two sorted arrays, and drop the duplicated one.
	 * 3.Finally, put values from these two String arrays to a new MyDlist and return
	 * 
	 * Because I use mergeSort there, the time complexity should be
	 * mlogm(mergesort string array1)+nlogn(mergesort string array1)
	 * Then the step to drop duplicates should be m or n, depends on which is larger
	 * All in all, the time complexity is mlogm
	 * 
	 * copmareWords returns a boolean value if string a1 is "smaller" than a2  
	 * by comparing ascii code of each char, we'll get the answer
	 * etc. "abc"<"bd" because "abc"[0]<"bd"[0] which is 'a'<'b'
	 * also "ab"<"abc"
	 */
	public static boolean compareWords(String a1,String a2){
		for (int i=0;(i<a1.length()&&i<a2.length()&&(!a2.equals(null))&&(!a1.equals(null)));i++){
			//the complexity of this part should be <=n
			if (a1.charAt(i)>a2.charAt(i))
				//comparing the ascii code
				return false;
			if (a1.charAt(i)<a2.charAt(i))			
				return true;
		}
		if(a1.length()<a2.length())
			return true;
	return false;
	}
	
	//there we use mergeSort
	public static void mergearray(String[] a, int first, int mid, int last)  
	{  
		//the time complexity of this part should be a constant(n)
	    int i = first;
	    int j = mid + 1;  
	    int k = 0;  
	    String[] temp = new String[a.length];	      
	    while (i <= mid && j <= last)  
	    {  
	    // there we have an array to store the sorted result 
	        if (compareWords(a[i], a[j]))  
	            temp[k++] = a[i++];  
	        else  
	            temp[k++] = a[j++];  
	    }  
	      
	    while (i <= mid)  
	        temp[k++] = a[i++];  
	      
	    while (j <= last)  
	        temp[k++] = a[j++];  
	 //a is the sorted array
	    for (i = 0; i < k; i++)  
	        a[first + i] = temp[i];  
	}  
	public static void mergesort(String[] a, int first, int last)  
	{  
		// this part is where we implement divide and conquer method
		// we divide this sorting problem into 2 problems
		// therefore O(nlogn)
	    if (first < last)  
	    {  
	        int mid = (first + last) / 2;  
	        mergesort(a, first, mid);    //sort left side  O(nlogn)
	        mergesort(a, mid + 1, last); //sort right side O(nlogn)
	        mergearray(a, first, mid, last); //merge O(n)
	    }  
	    //therefore this function is O(nlogn)
	}  
	
	public static MyDlist sort(MyDlist u)
	{//this function is to capsule all merge sort operations
	//it returns a sorted MyDlist
	//there time complexity should be O(nlogn)
	//the loop only operates n times but the operation 	mergesort(a1,0,a1.length-1) does nlogn times
		String[] a1 = new String[u.size];
		DNode temper = u.header.getNext();
		for (int i=0;i<u.size();i++)// O(n)
			{
			a1[i]=temper.getElement();
			temper=temper.getNext();
			}
		temper = u.header.getNext();
		mergesort(a1,0,a1.length-1);
		for (int i=0;i<u.size();i++)// O(n)
			{
			temper.setElement(a1[i]);
			temper = temper.getNext();
			}
		return u;
	}
	
	public static MyDlist union(MyDlist u, MyDlist v){
		//to get intersection, we merge sort and get the two new mydlist u and v
		//then compare each of them from 0~n, drop the duplicated value
		//and put the distinct strings to a new mydlist w
		//finall return w, w is the intersection
		//the time complexity for this function is nlogn because of merge sort
		MyDlist u1 = cloneList(u);
		MyDlist v1 = cloneList(v);
		u1 = sort(u1);// O(nlogn) because we use merge sort
		v1 = sort(v1);// O(nlogn)
		MyDlist w = new MyDlist();
		DNode x = u1.header.getNext();
		DNode y = v1.header.getNext();
		while(!x.equals(u1.trailer) && !y.equals(v1.trailer)){// O(n)
			//System.out.println(x.getElement());
			if (x.getElement().equals(y.getElement())){
				w.addLast(new DNode(x.getElement(),null,null));
				x=x.getNext();
				y=y.getNext();
			}
			else if (compareWords(x.getElement(), y.getElement())){
				w.addLast(new DNode(x.getElement(),null,null));
				x=x.getNext();
			}
			else{
				w.addLast(new DNode(y.getElement(),null,null));
				y=y.getNext();
			}
		}
		while (!x.equals(u1.trailer)){// O(n)
				w.addLast(new DNode(x.getElement(),null,null));
				x=x.getNext();	
		}
		while (!y.equals(v1.trailer)){// O(n)
				w.addLast(new DNode(y.getElement(),null,null));
				y=y.getNext();
		}
	return w;
	}
	
	public static MyDlist intersection(MyDlist u, MyDlist v){
		//to get intersection, we sort and get the two new mydlist u and v
		//then compare each of them from 0~n, copy the duplicated value
		//to a new mydlist called z
		//finall return z, z is the intersection, the time complexity is nlogn
		MyDlist u1 = cloneList(u);
		MyDlist v1 = cloneList(v);
		MyDlist z = new MyDlist();
		u1 = sort(u1);// O(nlogn)
		v1 = sort(v1);// O(nlogn)
		DNode x = u1.header.getNext();
		DNode y = v1.header.getNext();
		while(!x.equals(u1.trailer) && !y.equals(v1.trailer)){// O(n)
			if (x.getElement().equals(y.getElement())){
				z.addLast(new DNode(x.getElement(),null,null));
				x=x.getNext();
				y=y.getNext();
			}
			else if (compareWords(x.getElement(), y.getElement())){
					x=x.getNext();
			}
			else{
					y=y.getNext();
				}
		}
		return z;
	}
}
