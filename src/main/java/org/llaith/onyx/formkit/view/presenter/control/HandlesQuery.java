package org.llaith.onyx.formkit.view.presenter.control;

import com.llaith.query.QueryResults;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 21:15
 *
 * Used for things like the pickers.
 *
 */
public interface HandlesQuery<T> {

    public interface QueryHandler<T> {

        List<T> handleQuery(QueryResults<T> value);
    }

    void setQueryHanlder(QueryHandler<T> handler);

}
