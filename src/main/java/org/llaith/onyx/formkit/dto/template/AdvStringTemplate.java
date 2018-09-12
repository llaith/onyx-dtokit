package org.llaith.onyx.formkit.dto.template;


import com.llaith.dto.Dto;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 29-Aug-2009
 * Time: 21:11:58
 */
public class AdvStringTemplate<T extends Dto> implements Template<T> {


    private final String template;
    private final Class<?> lexerClass;

    public AdvStringTemplate(final String template) {
        this(template, DefaultTemplateLexer.class);
    }

    public AdvStringTemplate(final String template, final Class<?> lexerClass) {
        this.template = template;
        this.lexerClass = lexerClass;
    }

    @Override
    public String process( final T dto) {
        final StringTemplate template = new StringTemplate(this.template, this.lexerClass);

        final Map<String, Object> vals = dto.get();
        for (final String id : vals.keySet()) {
            template.setAttribute(id, vals.get(id));
        }

        return template.toString();

    }
}
