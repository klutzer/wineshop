package com.klutzer.wineshop.business;

public class BasicBean {

	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BasicBean) {
			BasicBean bean = (BasicBean) obj;
			if (id == 0 && bean.id == 0)
				return bean == this;
			return id == bean.id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id == 0 ? super.hashCode() : (int) id;
	}
	
}
