package com.example.administrator.books_app;

public class BookJYInfo {
	private int id;
	private int bookid;
	private String number;
	private String startdate;
	private String enddate;
	private int state;
	private String image;
	private String bookname;
	private String bookaddress;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getBookaddress() {
		return bookaddress;
	}
	public void setBookaddress(String bookaddress) {
		this.bookaddress = bookaddress;
	}
	public BookJYInfo(int id, int bookid, String number, String startdate,
			String enddate, int state, String image, String bookname,
			String bookaddress) {
		super();
		this.id = id;
		this.bookid = bookid;
		this.number = number;
		this.startdate = startdate;
		this.enddate = enddate;
		this.state = state;
		this.image = image;
		this.bookname = bookname;
		this.bookaddress = bookaddress;
	}
	
	
}
