import android.os.CountDownTimer;

/**
 * Created by Vignesh on 3/21/16.
 */
public abstract class CustomCountDownTimer {

	long millisInFuture =0;
	long countDownInterval =0;
	long millisRemaining = 0;
	boolean intervalReduced = false;

	CountDownTimer timer=null;
	boolean isPaused = true;

	public CustomCountDownTimer(long millisInFuture, long countDownInterval)
	{
		this.countDownInterval = countDownInterval;
		this.millisInFuture = millisInFuture;
		this.millisRemaining = millisInFuture;

	}


	public void CreateCDT(long addition)
	{
		millisRemaining +=addition;
		timer = new CountDownTimer(millisRemaining,countDownInterval) {
			@Override
				public void onTick(long millisUntilFinished) {
					millisRemaining = millisUntilFinished;
					if(intervalReduced)
						CustomCountDownTimer.this.OnTick(millisRemaining*2);
					else
						CustomCountDownTimer.this.OnTick(millisRemaining);
				}

			@Override
				public void onFinish() {
					CustomCountDownTimer.this.OnFinish();
				}
		};
	}


	public abstract void OnTick(long millisRemaining);

	public abstract void OnFinish();

	public void Cancel()
	{
		if(timer!=null)
		{
			timer.cancel();
		}
		this.millisRemaining = 0;
	}

	public synchronized  void Start()
	{
		if(isPaused == true)
		{
			CreateCDT(0);
			timer.start();
			isPaused = false;
		}
	}


	public void Pause()throws IllegalStateException
	{
		if(isPaused == false)
		{
			timer.cancel();
		}

		else
		{
			throw new IllegalStateException("Timer is already paused");
		}

		isPaused = true;
	}

	public void Update(long addition)
	{
		timer.cancel();
		CreateCDT(addition);
		timer.start();
	}

	public void UpdateInterval()
	{
		timer.cancel();
		intervalReduced = true;
		millisRemaining /= 2;
		countDownInterval = 500;
		CreateCDT(0);
		timer.start();

	}

}
