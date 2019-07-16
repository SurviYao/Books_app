package com.example.administrator.books_app;

import java.io.Serializable;

/**
 * 图书实体类
 * @author Administrator
 *
 */
public class Book implements Serializable {
	private int bookid;//书籍编号
	private String bookname;//书籍名称
	private String type;//书籍类型
	private String bookaddress;//出版社
	private String bookdate;//出版日期
	private String info;//简介
	private String author;//作者
	private String image;//图片地址
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBookaddress() {
		return bookaddress;
	}
	public void setBookaddress(String bookaddress) {
		this.bookaddress = bookaddress;
	}
	public String getBookdate() {
		return bookdate;
	}
	public void setBookdate(String bookdate) {
		this.bookdate = bookdate;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Book(int bookid, String bookname, String type, String bookaddress,
			String bookdate, String info, String author, String image) {
		super();
		this.bookid = bookid;
		this.bookname = bookname;
		this.type = type;
		this.bookaddress = bookaddress;
		this.bookdate = bookdate;
		this.info = info;
		this.author = author;
		this.image = image;
	}
	
	
}
