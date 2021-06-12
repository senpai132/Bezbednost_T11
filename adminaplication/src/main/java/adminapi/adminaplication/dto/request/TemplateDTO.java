package adminapi.adminaplication.dto.request;

public class TemplateDTO {

    private String type;

    public TemplateDTO(){}

    public TemplateDTO(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
