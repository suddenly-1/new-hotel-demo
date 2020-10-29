package com.suddenly.exception;

import com.suddenly.responseResult.IResultEnum;

public class CustomizeException extends RuntimeException {

    private IResultEnum resultEnum;

    public IResultEnum getResultEnum() {
        return resultEnum;
    }

    public CustomizeException(IResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public CustomizeException(String message, IResultEnum resultEnum) {
        super(message);
        this.resultEnum = resultEnum;
    }

    public CustomizeException(String message, Throwable cause, IResultEnum resultEnum) {
        super(message, cause);
        this.resultEnum = resultEnum;
    }

    public CustomizeException(Throwable cause, IResultEnum resultEnum) {
        super(cause);
        this.resultEnum = resultEnum;
    }

}
