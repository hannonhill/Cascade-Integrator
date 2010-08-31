package com.hannonhill.integrator

class Template {
	
	static belongsTo = [site: Site]
	
	String name
	
	String toString() {
		"$name"
	}

    static constraints = {
    }
}
