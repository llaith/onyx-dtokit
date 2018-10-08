package org.llaith.onyx.formkit.dto.ext.template;


import org.llaith.onyx.formkit.dto.Dto;

import java.io.Serializable;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Aug 15, 2008
 * Time: 10:44:04 AM
 */
public interface Template<T extends Dto> extends Serializable {

    public String process(T dto);

}
