package com.hannonhill.integrator

class Site {

	static hasMany = [templates: Template]
	
	String name
	
	String toString() {
		"$name"
	}
	
    static constraints = {
    }
}
