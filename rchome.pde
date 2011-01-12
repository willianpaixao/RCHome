void setup ()
{
  int i;
  int rd[] = {8, 9, 10, 11, 12, 13};
  int wr[] = {2, 3, 4, 5, 6, 7};

  for (i = 0; i < 6 ; i++)
  {
    pinMode (rd[i], INPUT);
    pinMode (wr[i], OUTPUT);
    digitalWrite (rd[i], LOW);
  }

  Serial.begin(9600);
}

void loop ()
{
  int i, recivied;
  int rd[] = {8, 9, 10, 11, 12, 13};
  int wr[] = {2, 3, 4, 5, 6, 7};

  if (Serial.available ())
  {
    recivied = Serial.peek () - 49;
    Serial.flush ();

    if (recivied == 65)
     printRead (read ());
   else if (recivied == 70)
     write (read ());
  }
}

void printRead (int recivied)
{
  int rd[] = {8, 9, 10, 11, 12, 13};
  Serial.println (digitalRead (rd[recivied]));
}

int read ()
{
  int i, recivied;
  while (!Serial.available ())
    i++;
  recivied = Serial.peek () - 49;
  Serial.flush ();
  
  return recivied;
}

void write (int recivied)
{
  int i;
  int rd[] = {8, 9, 10, 11, 12, 13};
  int wr[] = {2, 3, 4, 5, 6, 7};

    if (digitalRead (rd[recivied]) == LOW)
      digitalWrite (wr[recivied], HIGH);
    else
      digitalWrite (wr[recivied], LOW);
    
    if (recivied == -1)
      for (i = 0; i < 6; i++)
        digitalWrite (wr[i], LOW);
}
