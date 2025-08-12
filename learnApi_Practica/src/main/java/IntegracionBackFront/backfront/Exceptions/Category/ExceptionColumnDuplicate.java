package IntegracionBackFront.backfront.Exceptions.Category;

import lombok.Getter;

public class ExceptionColumnDuplicate extends RuntimeException {

    @Getter
    private String columnDuplicate;
    public ExceptionColumnDuplicate(String message) {
        super(message);
    }

    public ExceptionColumnDuplicate(String message, String columnDuplicate) {
        super(message);
        this.columnDuplicate = columnDuplicate;
    }
}
