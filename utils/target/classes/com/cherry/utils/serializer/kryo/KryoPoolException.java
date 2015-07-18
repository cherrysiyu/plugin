package com.cherry.utils.serializer.kryo;


public final class KryoPoolException extends RuntimeException {
	
	private static final long serialVersionUID = -2992257109597526961L;
	protected KryoPoolException(final String errorMessage, final Object... args) {
		super(String.format(errorMessage, args));
	}
	
	protected KryoPoolException(final String errorMessage, final Exception cause, final Object... args) {
		super(String.format(errorMessage, args), cause);
	}
	
	protected KryoPoolException(final Exception cause) {
		super(cause.getMessage(), cause);
	}
}
