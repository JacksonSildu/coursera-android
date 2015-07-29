package br.com.sildu.selfie.pojo;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Selfie implements Serializable {
	private static final long	serialVersionUID	= 1L;

	private String				name;
	private Bitmap				selfie;

	public Selfie() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Bitmap getSelfie() {
		return selfie;
	}

	public void setSelfie(Bitmap selfie) {
		this.selfie = selfie;
	}

}
