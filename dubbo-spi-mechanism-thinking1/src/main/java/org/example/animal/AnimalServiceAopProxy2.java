package org.example.animal;


public class AnimalServiceAopProxy2 implements AnimalService {

	private AnimalService animalService;

	public AnimalServiceAopProxy2(AnimalService animalService) {
		super();
		this.animalService = animalService;
	}

	@Override
	public void speak() {
		System.out.println("before...before.");
		animalService.speak();
		System.out.println("after..after...");
	}

}
