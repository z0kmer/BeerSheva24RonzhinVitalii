import { useEffect, useState } from "react";
interface DateTime {
   year: number;
  month: number;
  day: number;
   hour: number;
  minute: number;
  second: number; 
}

export default function useTimer(currentFullTime: Date, interval:number = 1000): DateTime {
  const [year, setYear] = useState<number>(currentFullTime.getFullYear());
  const [month, setMonth] = useState<number>(currentFullTime.getMonth());
  const [day, setDay] = useState<number>(currentFullTime.getDate());
  const [hour, setHour] = useState<number>(currentFullTime.getHours());
  const [minute, setMinute] = useState<number>(currentFullTime.getMinutes());
  const [second, setSecond] = useState<number>(currentFullTime.getSeconds());

  useEffect(() => {
    function tick() {
      currentFullTime.setTime(currentFullTime.getTime() + interval)
      setYear(currentFullTime.getFullYear());
      setMonth(currentFullTime.getMonth() + 1);
      setDay(currentFullTime.getDate());
      setHour(currentFullTime.getHours());
      setMinute(currentFullTime.getMinutes());
      setSecond(currentFullTime.getSeconds());
    }
    const intervalId = setInterval(tick, interval);
    return () => clearInterval(intervalId);
  }, []);
  return {year, month, day, hour, minute, second}
}