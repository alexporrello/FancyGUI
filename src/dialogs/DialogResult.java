package dialogs;

public enum DialogResult {
	CONFIRM(0), DENY(1), CANCEL(2), NONE_SELECTED(3);
	
	int i;
	
	DialogResult(int i) {
		this.i = i;
	}
}
