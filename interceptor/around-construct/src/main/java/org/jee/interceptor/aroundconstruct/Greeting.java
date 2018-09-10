package org.jee.interceptor.aroundconstruct;

/**
 * @author Radim Hanus
 */
public interface Greeting {
    boolean isConstructed();

    boolean isInitialized();

    Param getParam();
}
