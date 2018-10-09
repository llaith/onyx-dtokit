package org.llaith.onyx.formkit.dto.ext.template;


import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.llaith.onyx.formkit.dto.Dto;


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
    public String process(final T dto) {

        final StringTemplate st = new StringTemplate(
                this.template,
                this.lexerClass);

        dto.currentValues()
           .forEach(st::setAttribute);

        return st.toString();

    }

}
