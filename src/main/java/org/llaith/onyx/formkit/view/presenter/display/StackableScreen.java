package org.llaith.onyx.formkit.view.presenter.display;



import org.llaith.onyx.formkit.controller.ControllerDisposeAbortException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 12/01/2012
 * Time: 06:55
 */
public class StackableScreen implements Screen {

    private final Deque<Screen> stack = new ArrayDeque<Screen>();

    public StackableScreen(final Screen root) {
        this.stack.push(root);
    }

    @Override
    public void open() {
        this.stack.peek().open();
    }

    @Override
    public void willClose() throws ControllerDisposeAbortException {
        for (final Iterator<Screen> it = this.stack.descendingIterator(); it.hasNext();) {
            it.next().willClose();
        }
    }

    @Override
    public void close() {
        this.forceRewindAll();
    }

    @Override
    public void show() {
        this.stack.peek().show();
    }

    @Override
    public void hide() {
        this.stack.peek().hide();
    }

    public void pop() throws DisplayableCloseVetoException {
        this.stack.peek().willClose();
        forcePop();
    }

    public void forcePop() {
        if (this.stack.size() == 1) throw new RuntimeException("Cannot pop display stack root display");
        this.stack.peek().hide();
        this.stack.peek().close();
        this.stack.pop();
        this.stack.peek().show();
    }

    /*
     * This is usually used for breadcrumbs.
     * You can only rewind the presenter you hold. If you had breadcrumbs for 'nested' forms, that'd be a branch off
     * another stack and
     * you would need a separate link to that controllerstack to rewind, either in a different breadcrumbs object, or a
     * smart breadcrumbs
     * with multi nesting businesssupport - perhaps the 'drop a line' style i was thinking of. So wizards could be nested?
     */
    public void rewindTo(final Screen screen) throws DisplayableCloseVetoException {
        for (final Iterator<Screen> it = this.stack.descendingIterator(); it.hasNext();) {
            if (it.next() == screen) break;
            pop();
        }
    }

    public void rewindAll() throws DisplayableCloseVetoException {
        rewindTo(this.stack.getFirst());
    }

    public void forceRewindTo(final Screen screen) {
        for (final Iterator<Screen> it = this.stack.descendingIterator(); it.hasNext();) {
            if (it.next() == screen) break; // pop displays last one
            forcePop();
        }
    }

    public void forceRewindAll() {
        forceRewindTo(this.stack.getFirst());
    }

    public void push( final Screen screen) {
        this.stack.push(screen);
        screen.open();
        screen.show();
    }

}
