package com.dbms.buffer.management;

import java.util.HashMap;


public class CLOCK {

	private class Node<K, V>{
		K key; //will correspond to the actual data coming from the node
		V value; // will be the index of element coming from the stream
		boolean flag;
		Node next;
		
		public Node(K key, V value){
			this.key = key;
			this.value = value;
			this.flag = true;
			this.next = null;
		}
		public Node(){
			this.flag = true;
			this.next = null;
		}
	}
	
	HashMap<Object, Node> hm = new HashMap<>();
	int cacheSize = 3;
	Node pointer = null;
	Node head;
	
	public CLOCK(int value){
		if(value > 0)
			this.cacheSize = value;
		else
			this.cacheSize = 3; //default
		
		head = new Node();
		head.key = 0;
		head.next = head;
		pointer = head;
		
		for(int i = 1; i < this.cacheSize; i++){
			Node current = new Node();
			pointer.next = current;
			current.key = i;
			current.next = head;
			pointer = current;
			
		}
		pointer = pointer.next;
	}

	private <K, V>void insert(K key, V value) {
		//System.out.println(pointer.key + "," + pointer.flag);
		Node current = new Node(key, value);
		
		
		
		if(hm.containsKey(key)){ //cache hit case
			
			System.out.println("Cache hit for " + key + "," + value);
			Node old = hm.get(key);
			old.flag = true;
			
		}else{// cache miss
			
			System.out.println("Disk I/O for " + key + "," + value);
			if(hm.size() >= cacheSize){ //cache full, bring page in cache
				
				while(pointer.flag == true){
					pointer.flag = false;
					pointer = pointer.next;
				}
				hm.remove(pointer.key);
				
			}
			hm.put(key, pointer);
			pointer.flag = true;
			pointer.key = key;
			pointer.value = value;
			pointer = pointer.next;
		}
	}
	
	
	private void setHead(Node current) {
		// TODO Auto-generated method stub
		
	}

	private void remove(Node node) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		//int[] stream = new int[]{4, 7, 0, 7, 1, 0, 1, 2, 1, 2, 7, 1};
		//int[] stream = new int[]{1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4};
		Object[] stream = new Object[]{2, 3, 2, 1, "Hello", "World", "Hello", 5, 2, 4, 5, 3, 2, 5, 2};
		CLOCK clock = new CLOCK(3);
				
		for(int i = 0; i < stream.length; i++){
			clock.insert(stream[i], i);
		}
	}


}
