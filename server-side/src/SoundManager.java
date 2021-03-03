import sun.audio.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
  /*
  private Clip correctSound;
  private AudioDataStream test;
  private Clip incorrectSound;
  private Clip winSound;
  private Clip loseSound;
  private Clip tickTockSound;
  private Clip music;
  private Clip music2;
  private Clip menuMusic;
  private Clip rocky;
  private Clip[] sounds;
  private ContinuousAudioDataStream loop;
  private Clip clip;
  private boolean[] first;
  private boolean mute;
  */
  SoundManager(){
    /*
    mute = false;
    first = new boolean[9];
    for (int i = 0 ;i < 9 ; i++)
      first[i] = true;
    try{
      this.sounds = new Clip[9];
      music = AudioSystem.getClip();
      sounds[0] = music; 
      correctSound = AudioSystem.getClip();
      sounds[1] = correctSound;
      incorrectSound = AudioSystem.getClip();
      sounds[2] = incorrectSound;
      winSound = AudioSystem.getClip();
      sounds[3] = winSound;
      loseSound = AudioSystem.getClip();
      sounds[4] = loseSound;
      tickTockSound = AudioSystem.getClip();     
      sounds[5] = tickTockSound;
      music2 = AudioSystem.getClip();     
      sounds[6] = music2;
      menuMusic = AudioSystem.getClip();     
      sounds[7] = menuMusic;
      rocky = AudioSystem.getClip();
      sounds[8] = rocky;
      
    }catch(Exception ex){}
    */
  }
  
  public void setSounds(AudioInputStream[] sounds){ 
    /*
    try{       
      this.sounds[0].open(sounds[0]);
      this.sounds[1].open(sounds[1]);
      this.sounds[2].open(sounds[2]);
      this.sounds[3].open(sounds[3]);
      this.sounds[4].open(sounds[4]);
      this.sounds[5].open(sounds[5]); 
      this.sounds[6].open(sounds[6]);
      this.sounds[7].open(sounds[7]);
      this.sounds[8].open(sounds[8]);
    }catch(Exception ex){}
    */
  }
  public void stopBackgroundSound(){
    /*
    //if(!mute){
      try{
        sounds[5].stop();
        sounds[0].stop();  
        sounds[6].stop(); 
        
      }catch(Exception ex){}
    //}
    */
  }
  public void stopRocky(){
    /*
     try{
        sounds[8].stop();
     }
     catch(Exception ex){}
     */
  }
  public boolean checkMuted(){
    return false;
  }
  
  public void setMute(){
    //mute = true;
  }
  
  public void setUnMute(){    
    //mute = false;    
  }
  
  public void playBackgroundSound() {
    /*
    if(!mute){
      try{  
        
        sounds[0].loop(Clip.LOOP_CONTINUOUSLY);
        
      }catch(Exception ex){}
    }
    */
  }
  public void playTickTockSound() {
    /*
    if(!mute){
      try{  
        
        sounds[5].loop(Clip.LOOP_CONTINUOUSLY);
        
      }catch(Exception ex){}
    }
    */
  }
  public void playRocky(){
    /*
     if(!mute){
      try{ 
        
        sounds[8].loop(Clip.LOOP_CONTINUOUSLY);
        
      }catch(Exception ex){}
    }  
    */
  }
  public void playBackgroundSound2(){
    /*
    if(!mute){
      try{ 
        
        sounds[6].loop(Clip.LOOP_CONTINUOUSLY);
        
      }catch(Exception ex){}
    }
    */
  }
  public void playMenuMusic () {
    /*
     if(!mute){
        try{
           sounds[7].loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex){}
     }
     */
  }
  public void stopMenuMusic () {
    /*
     try{
        sounds[7].stop();
     } catch (Exception ex){}
     */
  }
  public void playSound(int soundID){
    /*
    if(!mute){
      try{
        if(first[soundID]){
          sounds[soundID].start();
          first[soundID] = false;  
          return;
        }
        sounds[soundID].loop(1);
      }catch(Exception ex){};
    }
    */
  }
}
