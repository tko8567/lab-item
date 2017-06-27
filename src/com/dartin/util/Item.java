package com.dartin.util;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Daniil Yurkov and Martin Sikora on 16.11.2016.
 */
public class Item implements Comparable, Serializable {

	public static final int VERSION = 1;
	private static final long serialVersionUID = 4563959675023590545L;

	public static final String NAME_NULL = "unknown";
	public static final String USAGE_NULL = "undefined";

	protected final String name;
	protected final String usage;
	protected LocalDate date;
	public final Size size;

	public enum Size{
			TINY, SMALL, NORMAL, LARGE, HUGE, UNDEFINED;
		
		public boolean larger(Size size){
			if (this == UNDEFINED || size == UNDEFINED) return false;
			return this.ordinal() > size.ordinal();
		}
		
		public boolean smaller(Size size){
			if (this == UNDEFINED || size == UNDEFINED) return false;
			return this.ordinal() < size.ordinal();
		}

		@Override
		public String toString() {
			return this.name().toLowerCase();
		}

		public static Size parseSize(String newSize){
			for (Size size : values()){
				if (newSize.toLowerCase().equals(size.toString())) return size;
			}
			throw new IllegalArgumentException
					("Cannot parse size from \"" + newSize + '"');
		}
	}
	
	public Item() {
		name = NAME_NULL;
		usage = USAGE_NULL;
		size = Size.UNDEFINED;
		date = LocalDate.now();
	}
	 
	public Item(String name, String usage, Size size, LocalDate date) {
		this.name = name;
		this.usage = usage;
		this.size = size;
		this.date = date;
	}

	public Item(String name, String usage, Size size) {
		this(name, usage, size, LocalDate.now());
	}

	public Item(String name, String usage, String size, LocalDate date) {
		this.name = name;
		this.usage = usage;
		this.size = Size.parseSize(size);
		this.date = date;
	}

	public Item(String name, String usage, String size) {
		this(name, usage, size, LocalDate.now());
	}

	public Item(Item item){
		name = item.name;
		usage = item.usage;
		size = item.size;
		date = item.date;
	}

	public String name() {
		return name;
	}
	public String usage() {
		return usage;
	}
	public Size size() {
		return size;
	}
	public LocalDate date(){return date;}

	@Override
	public String toString() {
		return "Item{" +
				"name='" + name + '\'' +
				", usage='" + usage + '\'' +
				", size=" + size +
				", date=" + date +
				'}';
	}

	@Override
	public int hashCode() {
		 return (name + usage + size.toString()).hashCode();
	 }

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Item)) return false;
		Item item = (Item)obj;
		return (this.name.equals(item.name)
				|| this.usage.equals(item.usage)
				|| this.size.equals(item.size)
				);
	}

	@Override
	public int compareTo(Object o) {
		if (this.size.larger(((Item)o).size)) return 1;
		else if (this.size.smaller(((Item)o).size)) return -1;
		else return 0;
	}
}
