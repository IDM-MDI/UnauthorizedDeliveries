package by.a1.unauthorizeddeliveries.aop;

import by.a1.unauthorizeddeliveries.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

import static by.a1.unauthorizeddeliveries.exception.ExceptionStatus.EMPTY_PAGE;
import static org.springframework.util.CollectionUtils.isEmpty;

@Aspect
@Component
@Slf4j
public class PageAspect {
    /**
     * This advice runs after the successful execution of specific service methods that return paginated results,
     * and checks whether the returned list is not null and has a size greater than 0.
     * @param result the list of paginated results returned by the advised method
     * @throws {@link ServiceException} if the returned list is null or has a size of 0
     */
    @AfterReturning(value =
            "execution(* by.a1.unauthorizeddeliveries.service.impl.UserServiceImpl.findUsers(int,int,String,String)) " +
                    "|| execution(* by.a1.unauthorizeddeliveries.service.impl.ItemServiceImpl.findItems(int,int,String,String))" +
                    "|| execution(* by.a1.unauthorizeddeliveries.service.impl.PostingServiceImpl.findPostings(int,int,String,String))",
            returning = "result")
    public void afterCommentsPage(List<?> result) throws ServiceException {
        if(isEmpty(result)) {
            throw new ServiceException(EMPTY_PAGE.toString());
        }
    }
}
