package com.example.turingmaschine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class TuringmaschineActivity extends Activity implements OnClickListener {

	private String standardFilename = "Turingmaschine";
	private String savedTMFilename = "SavedTM";
	private String filename;
	private EditText saveFileEditText;

	private int selectedItemTM;

	private static final String TAG = "TuringmaschineActivity";

	private float currentX = 0.0f;

	private Button stepByStepButton;
	private Button startButton;
	private Button resetButton;
	private CheckedTextView state_1_0, state_1_1, state_1_raute, state_2_0,
			state_2_1, state_2_raute, state_3_0, state_3_1, state_3_raute,
			state_4_0, state_4_1, state_4_raute, state_5_0, state_5_1,
			state_5_raute;
	protected TextView cell_1, cell_2, cell_3, cell_4, cell_5, cell_6, cell_7;

	ImageView readHead;
	Animation readHeadAnimation;

	private NumberPicker pickerState, pickerBit, pickerDirection;
	String[] arrayAdapterState, arrayAdapterBit, arrayAdapterVektor;

	private TextView[] textViewArray = new TextView[7];
	private CheckedTextView[] checkedTextViewArray = new CheckedTextView[15];

	private int currentState = 0;
	private int currentCell = 3;
	private boolean stopBit = false;

	CheckedTextView cellNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		readHead = (ImageView) findViewById(R.id.lesekopf);

		stepByStepButton = (Button) findViewById(R.id.btnStepByStep);
		stepByStepButton.setOnClickListener(this);
		startButton = (Button) findViewById(R.id.btnStart);
		startButton.setOnClickListener(this);
		resetButton = (Button) findViewById(R.id.btnReset);
		resetButton.setOnClickListener(this);

		cell_1 = (TextView) findViewById(R.id.cell_1);
		cell_2 = (TextView) findViewById(R.id.cell_2);
		cell_3 = (TextView) findViewById(R.id.cell_3);
		cell_4 = (TextView) findViewById(R.id.cell_4);
		cell_5 = (TextView) findViewById(R.id.cell_5);
		cell_6 = (TextView) findViewById(R.id.cell_6);
		cell_7 = (TextView) findViewById(R.id.cell_7);

		state_1_0 = (CheckedTextView) findViewById(R.id.state_1_0);
		state_1_0.setOnClickListener(this);
		state_1_1 = (CheckedTextView) findViewById(R.id.state_1_1);
		state_1_1.setOnClickListener(this);
		state_1_raute = (CheckedTextView) findViewById(R.id.state_1_raute);
		state_1_raute.setOnClickListener(this);
		state_2_0 = (CheckedTextView) findViewById(R.id.state_2_0);
		state_2_0.setOnClickListener(this);
		state_2_1 = (CheckedTextView) findViewById(R.id.state_2_1);
		state_2_1.setOnClickListener(this);
		state_2_raute = (CheckedTextView) findViewById(R.id.state_2_raute);
		state_2_raute.setOnClickListener(this);
		state_3_0 = (CheckedTextView) findViewById(R.id.state_3_0);
		state_3_0.setOnClickListener(this);
		state_3_1 = (CheckedTextView) findViewById(R.id.state_3_1);
		state_3_1.setOnClickListener(this);
		state_3_raute = (CheckedTextView) findViewById(R.id.state_3_raute);
		state_3_raute.setOnClickListener(this);
		state_4_0 = (CheckedTextView) findViewById(R.id.state_4_0);
		state_4_0.setOnClickListener(this);
		state_4_1 = (CheckedTextView) findViewById(R.id.state_4_1);
		state_4_1.setOnClickListener(this);
		state_4_raute = (CheckedTextView) findViewById(R.id.state_4_raute);
		state_4_raute.setOnClickListener(this);
		state_5_0 = (CheckedTextView) findViewById(R.id.state_5_0);
		state_5_0.setOnClickListener(this);
		state_5_1 = (CheckedTextView) findViewById(R.id.state_5_1);
		state_5_1.setOnClickListener(this);
		state_5_raute = (CheckedTextView) findViewById(R.id.state_5_raute);
		state_5_raute.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnStart:
			final Handler handler = new Handler();
			Runnable refresher = new Runnable() {

				@Override
				public void run() {
					if (!stopBit) {
						startTM();
						handler.postDelayed(this, 2000);
					}
				}
			};
			handler.postDelayed(refresher, 500);
			if (stopBit) {
				Toast.makeText(getApplicationContext(), R.string.toastEndTM,
						Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.btnStepByStep:
			startTM();
			break;
		case R.id.btnReset:
			Intent intent = new Intent(this, TuringmaschineActivity.class);
			startActivity(intent);
			break;
		case R.id.state_1_0:
			tableDialog(state_1_0);
			break;
		case R.id.state_1_1:
			tableDialog(state_1_1);
			break;
		case R.id.state_1_raute:
			tableDialog(state_1_raute);
			break;
		case R.id.state_2_0:
			tableDialog(state_2_0);
			break;
		case R.id.state_2_1:
			tableDialog(state_2_1);
			break;
		case R.id.state_2_raute:
			tableDialog(state_2_raute);
			break;
		case R.id.state_3_0:
			tableDialog(state_3_0);
			break;
		case R.id.state_3_1:
			tableDialog(state_3_1);
			break;
		case R.id.state_3_raute:
			tableDialog(state_3_raute);
			break;
		case R.id.state_4_0:
			tableDialog(state_4_0);
			break;
		case R.id.state_4_1:
			tableDialog(state_4_1);
			break;
		case R.id.state_4_raute:
			tableDialog(state_4_raute);
			break;
		case R.id.state_5_0:
			tableDialog(state_5_0);
			break;
		case R.id.state_5_1:
			tableDialog(state_5_1);
			break;
		case R.id.state_5_raute:
			tableDialog(state_5_raute);
			break;
		default:
			break;
		}
	}

	private void startTM() {
		textViewArray[0] = (TextView) findViewById(R.id.cell_1);
		textViewArray[1] = (TextView) findViewById(R.id.cell_2);
		textViewArray[2] = (TextView) findViewById(R.id.cell_3);
		textViewArray[3] = (TextView) findViewById(R.id.cell_4);
		textViewArray[4] = (TextView) findViewById(R.id.cell_5);
		textViewArray[5] = (TextView) findViewById(R.id.cell_6);
		textViewArray[6] = (TextView) findViewById(R.id.cell_7);

		checkedTextViewArray[0] = (CheckedTextView) findViewById(R.id.state_1_0);
		checkedTextViewArray[1] = (CheckedTextView) findViewById(R.id.state_1_1);
		checkedTextViewArray[2] = (CheckedTextView) findViewById(R.id.state_1_raute);
		checkedTextViewArray[3] = (CheckedTextView) findViewById(R.id.state_2_0);
		checkedTextViewArray[4] = (CheckedTextView) findViewById(R.id.state_2_1);
		checkedTextViewArray[5] = (CheckedTextView) findViewById(R.id.state_2_raute);
		checkedTextViewArray[6] = (CheckedTextView) findViewById(R.id.state_3_0);
		checkedTextViewArray[7] = (CheckedTextView) findViewById(R.id.state_3_1);
		checkedTextViewArray[8] = (CheckedTextView) findViewById(R.id.state_3_raute);
		checkedTextViewArray[9] = (CheckedTextView) findViewById(R.id.state_4_0);
		checkedTextViewArray[10] = (CheckedTextView) findViewById(R.id.state_4_1);
		checkedTextViewArray[11] = (CheckedTextView) findViewById(R.id.state_4_raute);
		checkedTextViewArray[12] = (CheckedTextView) findViewById(R.id.state_5_0);
		checkedTextViewArray[13] = (CheckedTextView) findViewById(R.id.state_5_1);
		checkedTextViewArray[14] = (CheckedTextView) findViewById(R.id.state_5_raute);

		String cellValueString = checkedTextViewArray[currentState].getText()
				.toString();// currentState=0;
		String[] splitedValueFromTable = cellValueString.split(",");

		if (splitedValueFromTable[0].trim().equals("END")) {// stopBit=false;
			stopBit = true;
			Toast.makeText(getApplicationContext(), R.string.toastEndTM,
					Toast.LENGTH_LONG).show();
			
			for (int i = 0; i < checkedTextViewArray.length; i++) {
				checkedTextViewArray[i].setBackgroundResource(R.drawable.cell_shape);
			}
			checkedTextViewArray[currentState].setBackgroundResource(R.drawable.current_cell_shape);

		}
		// -----------------------------------------
		if (stopBit == false) {
			
			for (int i = 0; i < checkedTextViewArray.length; i++) {
				checkedTextViewArray[i].setBackgroundResource(R.drawable.cell_shape);
			}
			
			checkedTextViewArray[currentState].setBackgroundResource(R.drawable.current_cell_shape);
			
			textViewArray[currentCell].setText(splitedValueFromTable[1]);// currentCell=3;
			// ---------------------------------------------
			String getValueFromNaxtCell;
			if (splitedValueFromTable[2].trim().equals("L")) {
				getValueFromNaxtCell = textViewArray[currentCell - 1].getText()
						.toString();
				;
			} else {
				getValueFromNaxtCell = textViewArray[currentCell + 1].getText()
						.toString();
			}
			// --------------------------------------------------
			String[] splitedState = splitedValueFromTable[0].split("Q");
			if (getValueFromNaxtCell.trim().equals("0")) {
				switch (Integer.parseInt(splitedState[1])) {
				case 1:
					currentState = 0;// checkedTextViewArray[0]
					break;
				case 2:
					currentState = 3;// checkedTextViewArray[3]
					break;
				case 3:
					currentState = 6;// checkedTextViewArray[6]
					break;
				case 4:
					currentState = 9;// checkedTextViewArray[9]
					break;
				case 5:
					currentState = 12;// checkedTextViewArray[12]
					break;
				}
				// currentState=Integer.parseInt(splitedState[1])-1+2;/*set next
				// current state*/
			} else if (getValueFromNaxtCell.trim().equals("1")) {
				switch (Integer.parseInt(splitedState[1])) {
				case 1:
					currentState = 1;// checkedTextViewArray[1]
					break;
				case 2:
					currentState = 4;// checkedTextViewArray[4]
					break;
				case 3:
					currentState = 7;// checkedTextViewArray[7]
					break;
				case 4:
					currentState = 10;// checkedTextViewArray[10]
					break;
				case 5:
					currentState = 13;// checkedTextViewArray[13]
					break;
				}
				// currentState=Integer.parseInt(splitedState[1])+0+2;/*set next
				// current state*/
			} else if (getValueFromNaxtCell.trim().equals("#")) {
				switch (Integer.parseInt(splitedState[1])) {
				case 1:
					currentState = 2;// checkedTextViewArray[2]
					break;
				case 2:
					currentState = 5;// checkedTextViewArray[5]
					break;
				case 3:
					currentState = 8;// checkedTextViewArray[8]
					break;
				case 4:
					currentState = 11;// checkedTextViewArray[11]
					break;
				case 5:
					currentState = 14;// checkedTextViewArray[12]
					break;
				}
			}
			// ---------------------------------------------------
			if (splitedValueFromTable[2].trim().equals("L")) {
				currentCell--;
				moveToLeft();
			} else {
				currentCell++;
				moveToRight();
			}

		} // end !if equals END
	}

	private void tableDialog(final CheckedTextView cellNumber) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, android.R.style.Animation_Dialog));
		final LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		final View dialogView = inflater.inflate(R.layout.table_dialog_layout,
				null);
		arrayAdapterState = getResources().getStringArray(R.array.arrState);
		arrayAdapterBit = getResources().getStringArray(R.array.arrBit);
		arrayAdapterVektor = getResources().getStringArray(R.array.arrVektor);
		pickerState = (NumberPicker) dialogView
				.findViewById(R.id.numberPickerState);
		pickerBit = (NumberPicker) dialogView
				.findViewById(R.id.numberPickerBit);
		pickerDirection = (NumberPicker) dialogView
				.findViewById(R.id.numberPickerVektor);

		pickerState.setMaxValue(arrayAdapterState.length - 1);
		pickerState.setDisplayedValues(arrayAdapterState);
		pickerState.setMinValue(0);
		pickerState
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		pickerBit.setMaxValue(arrayAdapterBit.length - 1);
		pickerBit.setDisplayedValues(arrayAdapterBit);
		pickerBit.setMinValue(0);
		pickerBit
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		pickerDirection.setMaxValue(arrayAdapterVektor.length - 1);
		pickerDirection.setDisplayedValues(arrayAdapterVektor);
		pickerDirection.setMinValue(0);
		pickerDirection
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		builder.setView(dialogView);

		builder.setPositiveButton(R.string.btnOk,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (!arrayAdapterState[pickerState.getValue()]
								.equals("END")) {
							cellNumber.setText(arrayAdapterState[pickerState
									.getValue()]
									+ ", "
									+ arrayAdapterBit[pickerBit.getValue()]
									+ ", "
									+ arrayAdapterVektor[pickerDirection
											.getValue()]);
						} else {
							cellNumber.setText(arrayAdapterState[pickerState
									.getValue()]);
						}
					}
				});
		builder.setNegativeButton(R.string.btnCancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();

					}
				});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	private void saveDialog() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, android.R.style.Animation_Dialog));
		final LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View dialogView = inflater.inflate(R.layout.save_dialog_layout,
				null);
		saveFileEditText = (EditText) dialogView
				.findViewById(R.id.editTextSaveFile);

		builder.setTitle(R.string.headerFilename);
		builder.setView(dialogView);
		builder.setPositiveButton(R.string.btnOk,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						filename = saveFileEditText.getText().toString();
						if (filename.equalsIgnoreCase("")) {
							setFilename(standardFilename);
						}
						try {
							createSavedTMFile(filename);
							createLocalFile(filename);
						} catch (IOException e) {
							Log.e(TAG, "createLocalFile(filename) - failed!", e);
						}
						Toast.makeText(
								getApplicationContext(),
								getResources().getString(
										R.string.toastFileSaved, filename),
								Toast.LENGTH_LONG).show();
					}
				});
		builder.setNegativeButton(R.string.btnCancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();

					}
				});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	private void loadDialog() throws FileNotFoundException {

		File file = getApplicationContext().getFileStreamPath(
				savedTMFilename + ".txt");
		if (!file.exists()) {
			Toast.makeText(getApplicationContext(), R.string.toastNoTM,
					Toast.LENGTH_LONG).show();
		}

		final CharSequence[] tmList = readSavedTMNames();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.headerLoadFile);
		selectedItemTM = -1;
		builder.setSingleChoiceItems(tmList, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						selectedItemTM = item;
					}
				});
		builder.setPositiveButton(R.string.btnOk,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (selectedItemTM != -1) {
							try {
								loadTMFromLocalFile(tmList[selectedItemTM]);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.toastSelectedTM,
											tmList[selectedItemTM]),
									Toast.LENGTH_SHORT).show();
						}
						dialog.dismiss();

					}
				});
		builder.setNegativeButton(R.string.btnCancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	private float getStepLength(){
		float xCell_1 = cell_1.getX();
		float xCell_2 = cell_2.getX();
		return xCell_2-xCell_1;
	} 	
	
	private void moveToLeft() {

		getStepLength();
		
		float currentPosition = getCurrentX();

		TranslateAnimation animation = new TranslateAnimation(currentPosition,
				(currentPosition - getStepLength()), 0.0f, 0.0f); // new TranslateAnimation(xFrom,xTo,
											// yFrom,yTo)
		animation.setDuration(2000); // animation duration
		animation.setFillAfter(true);

		readHead.startAnimation(animation); // start animation
		setCurrentX(currentPosition - getStepLength());

	}

	private void moveToRight() {

		float currentPosition = getCurrentX();

		TranslateAnimation animation = new TranslateAnimation(currentPosition,
				(currentPosition + getStepLength()), 0.0f, 0.0f); // new TranslateAnimation(xFrom,xTo,
		// yFrom,yTo)
		animation.setDuration(2000); // animation duration
		animation.setFillAfter(true);

		readHead.startAnimation(animation); // start animation
		setCurrentX(currentPosition + getStepLength());
	}

	private void createLocalFile(String filename) throws IOException {

		BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(openFileOutput(filename + ".txt",
						Context.MODE_PRIVATE)));

		String[] tableContent = { state_1_0.getText().toString(),
				state_1_1.getText().toString(),
				state_1_raute.getText().toString(),
				state_2_0.getText().toString(), state_2_1.getText().toString(),
				state_2_raute.getText().toString(),
				state_3_0.getText().toString(), state_3_1.getText().toString(),
				state_3_raute.getText().toString(),
				state_4_0.getText().toString(), state_4_1.getText().toString(),
				state_4_raute.getText().toString(),
				state_5_0.getText().toString(), state_5_1.getText().toString(),
				state_5_raute.getText().toString() };

		try {
			for (String currentCellValue : tableContent) {
				bufferedWriter.write(currentCellValue + "\r\n");
			}

			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (Exception e) {
			Log.e(TAG, "createFile(String filename) - failed!");
			e.printStackTrace();
		}
	}

	private void createSavedTMFile(String savingfilename) throws IOException {

		File file = getApplicationContext().getFileStreamPath(
				savingfilename + ".txt");

		if (!file.exists()) {
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(openFileOutput(savedTMFilename
							+ ".txt", Context.MODE_APPEND)));

			try {
				bufferedWriter.write(savingfilename + ";");

				bufferedWriter.flush();
				bufferedWriter.close();
			} catch (Exception e) {
				Log.e(TAG, "createSavedTMFile(String savingfilename) - failed!");
				e.printStackTrace();
			}
		}

	}

	public void loadTMFromLocalFile(CharSequence filename)
			throws FileNotFoundException {

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(openFileInput(filename.toString()
						+ ".txt")));

		try {
			state_1_0.setText(bufferedReader.readLine());
			state_1_1.setText(bufferedReader.readLine());
			state_1_raute.setText(bufferedReader.readLine());
			state_2_0.setText(bufferedReader.readLine());
			state_2_1.setText(bufferedReader.readLine());
			state_2_raute.setText(bufferedReader.readLine());
			state_3_0.setText(bufferedReader.readLine());
			state_3_1.setText(bufferedReader.readLine());
			state_3_raute.setText(bufferedReader.readLine());
			state_4_0.setText(bufferedReader.readLine());
			state_4_1.setText(bufferedReader.readLine());
			state_4_raute.setText(bufferedReader.readLine());
			state_5_0.setText(bufferedReader.readLine());
			state_5_1.setText(bufferedReader.readLine());
			state_5_raute.setText(bufferedReader.readLine());

			bufferedReader.close();
		} catch (IOException e) {
			Log.e(TAG, "readValuesFromLocalFile() failed!");
			e.printStackTrace();
		}
	}

	private CharSequence[] readSavedTMNames() throws FileNotFoundException {
		String name = null;
		CharSequence[] names = null;

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(openFileInput(savedTMFilename + ".txt")));

		try {
			name = bufferedReader.readLine();
			names = name.split(";");
			bufferedReader.close();
		} catch (IOException e) {
			Log.e(TAG, "readSavedTMNames() - failed!");
			e.printStackTrace();
		}

		return names;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settingsSave:
			saveDialog();
			return true;
		case R.id.settingsLoad:
			try {
				loadDialog();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return true;
//		case R.id.settingsRemove:
//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public float getCurrentX() {
		return currentX;
	}

	public void setCurrentX(float currentX) {
		this.currentX = currentX;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
