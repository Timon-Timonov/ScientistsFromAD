package Exceptions;

public interface ListExceptions {

	class ComponentsNotFound extends Exception {
		public ComponentsNotFound() {
			super("Utils.Component not found. Ny bivaet i takoe.");
		}
	}
}
