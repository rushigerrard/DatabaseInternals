package com.dbms.buffer.management;

import java.util.HashMap;

public class LRU {

	
	private class Node{
		int key; //will correspond to the actual data coming from the node
		int value; // will be the index of element coming from the stream
		Node prev;
		Node next;
		
		public Node(int key, int value){
			this.key = key;
			this.value = value;
			prev = null;
			next = null;
		}
	}

	HashMap<Integer, Node> hm = new HashMap<>();
	int cacheSize = 3;
	Node head = null;
	Node tail = null;
	
	public LRU(int value){
		
		if(value > 0)
			this.cacheSize = value;
		else
			this.cacheSize = 3; //default
		
	}
	
	public void insert(int key, int value){
		Node current = new Node(key, value);
		
		if(hm.containsKey(key)){ //cache hit case
			System.out.println("Cache hit for " + key + "," + value);
			remove(hm.get(key));
			hm.remove(key);
			
		}else{// cache miss
			System.out.println("Disk I/O for " + key + "," + value);
			if(hm.size() >= cacheSize){ //cache full, bring page in cache
				hm.remove(tail.key);
				remove(tail);
			}
			
		}
		setHead(current);
		hm.put(key, current);
	}
	
	
	private void setHead(Node current) {
		current.next = head;
		current.prev = null;
		if(head != null){
			head.prev = current;
		}
		head = current;
		if(tail == null)
			tail = current;
	}

	private void remove(Node toRemove) {
		if(toRemove.prev == null){	//Removing head 
            head = toRemove.next;
        }else{						//Removing otherwise
        	toRemove.prev.next = toRemove.next;
        }
 
        if(toRemove.next == null){	//Removing tail
        	tail = toRemove.prev;
        }else{						//Removing otherwise
        	toRemove.next.prev = toRemove.prev;
        }
 
	}

	public static void main(String[] args) {
		//int[] stream = new int[]{4, 7, 0, 7, 1, 0, 1, 2, 1, 2, 7, 1};
		//int[] stream = new int[]{1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4};
		//int[] stream = new int[]{7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};
		int[] stream = new int[]{2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2};
		LRU lru = new LRU(3);
		
		for(int i = 0; i < stream.length; i++){
			lru.insert(stream[i], i);
		}
		
	}

}
