package com.scholanova.projectstore.models;

public class Stocks {

    private Integer id;
    private String name;
    private String type;
    private Integer value;

    public Stocks() {
    }

    public Stocks(Integer id, String name, String type, Integer value) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

    
}
