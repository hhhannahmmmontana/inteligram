import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import org.jetbrains.annotations.NotNull;

public class inteligram extends AnAction {
    static final int kMaxSymbols = 4083;
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        assert editor != null;
        int start;
        if (editor.getSelectionModel().hasSelection()) {
            start = editor.getSelectionModel().getSelectionEnd();
        } else {
            start = 0;
        }
        int size = editor.getDocument().getTextLength() - start;
        int end;
        if (kMaxSymbols < size) {
            end = kMaxSymbols;
            char symbol = editor.getDocument().getText().charAt(start + end);
            while (symbol != '\n' && symbol != '}') {
                if (end == 0) {
                    end = kMaxSymbols;
                    break;
                }
                --end;
                symbol = editor.getDocument().getText().charAt(start + end);
            }
            if (end < kMaxSymbols) {
                ++end;
            }
        } else {
            end = size;
        }
        editor.getSelectionModel().setSelection(start, start + end);
        String result_string = "```java\n" + editor.getSelectionModel().getSelectedText() + "\n```";
        StringSelection result = new StringSelection(result_string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(result, null);
        BrowserUtil.browse("tg://msg?text=" + result_string);
    }
}
