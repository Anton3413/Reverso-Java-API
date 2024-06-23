package reverso.data.response;


public abstract class Response {

    private boolean isOK;

    private String sourceLanguage;

    private String text;

    private String errorMessage;

    public Response(boolean isOK, String errorMessage, String sourceLanguage, String text) {
        this.isOK = isOK;
        this.errorMessage = errorMessage;
        this.sourceLanguage = sourceLanguage;
        this.text = text;
    }

    public Response(boolean isOK, String sourceLanguage, String text) {
        this.isOK = isOK;
        this.sourceLanguage = sourceLanguage;
        this.text = text;
        this.errorMessage=null;
    }

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"isOK\": " + isOK + ",\n" +
                "  \"sourceLanguage\": \"" + sourceLanguage + "\",\n" +
                "  \"text\": \"" + text + "\",\n" +
                "  \"errorMessage\": \"" + errorMessage + "\"\n" +
                "}";
    }
}
