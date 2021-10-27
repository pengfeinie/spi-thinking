package org.example.animal;

public class AnimalServiceAopProxy implements AnimalService {

	private AnimalService animalService;

	public AnimalServiceAopProxy(AnimalService animalService) {
		super();
		this.animalService = animalService;
	}

	@Override
	public void speak() {
		System.out.println("before....");
		animalService.speak();
		System.out.println("after.....");
	}

}
