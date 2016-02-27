package todolist.model;

public class NormalCommandHandler {
    
    private DataBase dataBase;
    private UIhandler uiHandler;
    
    public NormalCommandHandler(Database dataBase, UIHandler uiHandler) {
        this.dataBase = dataBase;
        this.uiHandler = uiHandler;
    }
    
    public void execute(NormalCommand normalCommand) {
		// TODO Auto-generated method stub
		String action = normalCommand.getAction();
		String arg[] = normalCommand.getArgs();
		switch(action) {
		    case "add":
		        String type = arg[0];
		        switch(type) {
		            case "event": addEvent(arg[1], arg[2], arg[3], arg[4], arg[5]);
		            case "deadline": addDeadline(arg[1], arg[2], arg[3]);
		            case "task": addTask(arg[1]);
		        }
		    case "edit": edit(arg[0], arg[1], arg[2]);
		    case "delete": delete(arg[0]);
		    case "search": search(arg[0]);
		    case "filter": filter(arg[0]);
		    case "sort": sort(arg[0], arg[1]);
		    case "insert": insert(arg[0], arg[1], arg[2]);
		    case "switchPosition": switchPosition(arg[0], arg[1]);
		    case "label": label(arg[0], arg[1]);
		    case "postpone": postpone(arg[0], arg[1], arg[2]);
		    case "forward": forward(arg[0], arg[1], arg[2]);
		    case "add-remind": addRemind(arg);
		    case "remind": remind(arg);
		    case "add-remind-bef": addRemindBef(arg);
		    case "remind-bef": remindBef(arg[0], arg[1], arg[2]);
		    case "exit": exit();
		    case "undo": undo(Integer.parseInt(arg[0]));
		    case "redo": redo(Integer.parseInt(arg[0]));
		}
	}
    
	private void addEvent(String title, String startDate, String startTime, String quantity, String timeUnit) {
	    Name name = new Name(title);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    LocalDateTime start = LocalDateTime.parse(startDate + " " + startTime, formatter);
	    LocalDateTime end = start.(Long.parseLong(quantity), generateTimeUnit(timeUnit));
	    Task newEvent = new Task(name, start, end, null, null, null, false);
	    
	    dataBase.add(newEvent);
	    uiHandler.refresh();
	    uiHandler.highLight(newEvent);
	    uiHandler.sendMessage("successfully added");
	}
	
	private void addDeadline(String title, String endDate, String endTime) {
	    Name name = new Name(title);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    LocalDateTime end = LocalDateTime.parse(endDate + " " + endTime, formatter);
	    Task newEvent = new Task(name, null, end, null, null, null, false);
	    
	    dataBase.add(newEvent);
	    uiHandler.refresh();
	    uiHandler.highLight(newEvent);
	    uiHandler.sendMessage("successfully added");
	}
	
	private void addTask(String title) {
	    Name name = new Name(title);
	    Task newEvent = new Task(name, null, null, null, null, null, false);
	    
	    dataBase.add(newEvent);
	    uiHandler.refresh();
	    uiHandler.highLight(newEvent);
	    uiHandler.sendMessage("successfully added");
	}
	
	private void done(String title) {
	    Task tempTask = new Task(dataBase.retreive(new SearchCommand("Name", title).get(0));
	    dataBase.delete(tempTask);
	    
	    tempTask.setDoneStatus(true);
	    dataBase.add(tempTask);
	    
	    uiHandler.refresh();
	    uiHandler.highLight(tempTask);
	    uiHandler.sendMessage("successfully marked");
	}
	
	private void edit(String title, String fieldName, String newValue) {
	    Task tempTask = new Task(dataBase.retreive(new SearchCommand("Name", title).get(0));
	    dataBase.delete(tempTask);
	   
	    switch(fieldName) {
	        "title": tempTask.setName(newValue);
	        "done": tempTask.setDoneStatus(true);
	        "undone": tempTask.setDoneStatus(false);
	        "start-time": DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
	        LocalDateTime start = LocalDateTime.parse(newValue, formatter);
	        tempTask.setStartTime(start);
	        "end-time": DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
	        LocalDateTime end = LocalDateTime.parse(newValue, formatter);
	        tempTask.setEndTime(end);
	    }
	    
        dataBase.add(tempTask);
	    
	    uiHandler.refresh();
	    uiHandler.highLight(tempTask);
	    uiHandler.sendMessage("successfully changed");
        
	}
	
	private void delete(String title) {
	    Task tempTask = new Task(dataBase.retreive(new SearchCommand("Name", title));
	    dataBase.delete(tempTask);
	    
	    uiHandler.refresh();
	    uiHandler.sendMessage("successfully deleted");
	}
	
	private void search(String title) {
	    uiHandler.search(title);
	}
	
	private void filter(String category) {
	    uiHandler.filter(category);
	}
	
	private void sort(String fieldName, String order) {
	    uiHandler.sort(fieldName, order);
	}
	
	private void insert(String title, String befaft, String title) {
	    uiHandler.insert(title, befaft, title);
	}
	
	private void switchPosition(String title1, String title2) {
	    uiHandler.insert(title, "aft", title);
	}
	
	private void label(String title, String category) {
	    Task tempTask = new Task(dataBase.retreive(new SearchCommand("Name", title).get(0));
	    dataBase.delete(tempTask);
	    
	    tempTask.setCategory(new Category(category));
	    dataBase.add(tempTask);
	    
	    uiHandler.refresh();
	    uiHandler.highLight(tempTask);
	}
	
	private void postpone(String title, String quantity, String timeUnit) {
	    Task tempTask = new Task(dataBase.retreive(new SearchCommand("Name", title).get(0));
	    dataBase.delete(tempTask);

        if(tempTask.getStartTime() == null) {
            LocalDateTime tempEndTime = temp.getEndTime();
            tempEndTime = tempEndTime.plus(Long.parseLong(quantity), generateTimeUnit(timeUnit));
            
            tempTask.setEndTime(tempEndTime);
            dataBase.add(tempTask);
            
            uiHandler.refresh();
            uiHandler.highLight(tempTask);
        } else {
            LocalDateTime tempStartTime = temp.getStartTime();
            LocalDateTime tempEndTime = temp.getEndTime();
            tempStartTime = tempStartTime.plus(Long.parseLong(quantity), generateTimeUnit(timeUnit));
            tempEndTime = tempEndTime.plus(Long.parseLong(quantity), generateTimeUnit(timeUnit));
            
            tempTask.setStartTime(tempStartTime);
            tempTask.setEndTime(tempEndTime);
            
            dataBase.add(tempTask);
            
            uiHandler.refresh();
            uiHandler.highLight(tempTask);
        }
	}
	
	private void forward(String title, String quantity, String timeUnit) {
	    Task tempTask = new Task(dataBase.retreive(new SearchCommand("Name", title).get(0));
	    dataBase.delete(tempTask);
        
        if(tempTask.getStartTime() == null) {
            LocalDateTime tempEndTime = temp.getEndTime();
            tempEndTime = tempEndTime.minus(Long.parseLong(quantity), generateTimeUnit(timeUnit));
            
            tempTask.setEndTime(tempEndTime);
            dataBase.add(tempTask);
            
            uiHandler.refresh();
            uiHandler.highLight(tempTask);
        } else {
            LocalDateTime tempStartTime = temp.getStartTime();
            LocalDateTime tempEndTime = temp.getEndTime();
            tempStartTime = tempStartTime.minus(Long.parseLong(quantity), generateTimeUnit(timeUnit));
            tempEndTime = tempEndTime.minus(Long.parseLong(quantity), generateTimeUnit(timeUnit));
            
            tempTask.setStartTime(tempStartTime);
            tempTask.setEndTime(tempEndTime);
            
            dataBase.add(tempTask);
            
            uiHandler.refresh();
            uiHandler.highLight(tempTask);
        }
	}
	
	private void addRemind(String[] arg) {
	    String type = arg[0];
		  switch(type) {
		      case "event": addEvent(arg[1], arg[2], arg[3], arg[4], arg[5]);
		      case "deadline": addDeadline(arg[1], arg[2], arg[3]);
		      case "task": addTask(arg[1]);
		  }
		  
		  remind(arg[1]);
	}
	
	private void addRemindBef(String[] arg, String quantity, String timeUnit) {
	    String type = arg[0];
		  switch(type) {
		      case "event": addEvent(arg[1], arg[2], arg[3], arg[4], arg[5]);
		      case "deadline": addDeadline(arg[1], arg[2], arg[3]);
		      case "task": addTask(arg[1]);
		  }
		  
		  remindBef(arg[1], quantity, timeUnit);
	}
	
	private void remindBef(String title, String quantity, String timeUnit) {
	    if(duration == null) {
	        if(tempTask.getStartTime() == null) {
                LocalDateTime reminderTime = temp.getStartTime();
            } else {
                LocalDateTime reminderTime = temp.getEndTime();
            }
	    } else {
	        if(tempTask.getStartTime() == null) {
                LocalDateTime reminderTime = temp.getStartTime().minus(Long.parseLong(quantity), generateTimeUnit(timeUnit);
            } else {
                LocalDateTime reminderTime = temp.getEndTime().minus(Long.parseLong(quantity), generateTimeUnit(timeUnit);
            }
	    }
	    
	    Task tempTask = new Task(dataBase.retreive(new SearchCommand("Name", title).get(0));
	    
	    dataBase.delete(tempTask);
        
        Reminder newReminder = new Reminder(true, reminderTime);
        
        tempTask.setReminder(new Reminder);
        
        dataBase.add(tempTask);
        
        uiHandler.refresh();    
        uiHandler.highLight(tempTask);
	}
	
	private void remind(String title) {
	    remindBef(title, null, null);
	}
	
	private void exit() {
	    uiHandler.exit();
	}
	
	
	
	private void undo(int steps) {
	    dataBase.undo(steps);
	    uiHandler.refresh();
	}
	
	private void redo(int steps) {
	    dataBase.redo(steps);
	    uiHandler.refresh();
	}
	
	private void generateTimeUnit(String unit) {
	    switch(unit) {
	        case "day": return TimeUnit.DAYS;
	        case "hour": return TimeUnit.HOURS;
	        case "minute": return TimeUnit.MINUTES;
	    }
	}
}

