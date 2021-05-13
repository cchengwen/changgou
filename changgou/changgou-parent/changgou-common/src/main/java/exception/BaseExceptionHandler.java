package exception;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 * @Author WEN
 * @Date 2020/11/7 22:03
 */
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * 异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
