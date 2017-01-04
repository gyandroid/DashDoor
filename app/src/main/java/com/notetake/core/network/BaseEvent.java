package com.notetake.core.network;


public class BaseEvent {

    public static final String UNHANDLED_MSG = "UNHANDLED_MSG";
    public static final int UNHANDLED_CODE = -1;

    protected static class OnRequest<Request> {
        private Request mRequest;

        public OnRequest(Request request) {
            mRequest = request;
        }

        public Request getRequest() {
            return mRequest;
        }
    }

    protected static class OnSuccess<Response> {

        private Response mResponse;

        public OnSuccess(Response response) {
            mResponse = response;
        }

        public Response getResponse() {
            return mResponse;
        }

    }

    protected static class OnFailure {

        private String mErrorMessage;
        private int mCode;

        public OnFailure(String errorMessage, int code) {
            mErrorMessage = errorMessage;
            mCode = code;
        }

        public String getErrorMessage() {
            return mErrorMessage;
        }

        public int getCode() {
            return mCode;
        }

    }

}