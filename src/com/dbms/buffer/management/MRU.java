package com.dbms.buffer.management;

import java.util.HashMap;

public class MRU{

	
	private class Node<K, V>{
		K key; //will correspond to the actual data coming from the node
		V value; // will be the index of element coming from the stream
		Node prev;
		Node next;
		
		public Node(K key, V value){
			this.key = key;
			this.value = value;
			prev = null;
			next = null;
		}
	}

	HashMap<Object, Node> hm = new HashMap<>();
	int cacheSize = 3;
	Node head = null;
	Node tail = null;
	
	public MRU(int value){
		
		if(value > 0)
			this.cacheSize = value;
		else
			this.cacheSize = 3; //default
		
	}
	
	public <K, V>void insert(K key, V value){
		Node current = new Node(key, value);
		
		if(hm.containsKey(key)){ //cache hit case
			System.out.println("Cache hit for " + key + "," + value);
			remove(hm.get(key));
			hm.remove(key);
			
		}else{// cache miss
			System.out.println("Disk I/O for " + key + "," + value);
			if(hm.size() >= cacheSize){ //cache full, bring page in cache
				hm.remove(head.key);
				remove(head);
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
		Object[] stream = new Object[]{2, 3, 2, "Asha", "Lata", 2, 5, "Lata", "Asha", 2, 4, 5, 3, 2, 5, 2};
		MRU mru = new MRU(3);
		
		for(int i = 0; i < stream.length; i++){
			mru.insert(stream[i], i);
		}
		
	}

}
