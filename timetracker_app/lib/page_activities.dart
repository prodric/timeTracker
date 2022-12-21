import 'package:timetracker_app/tree.dart';
import 'package:flutter/material.dart';
import 'package:timetracker_app/PageIntervals.dart';
import 'package:timetracker_app/page_report.dart';

class PageActivities extends StatefulWidget {
  const PageActivities({Key? key}) : super(key: key);

  @override
  State<PageActivities> createState() => _PageActivitiesState();
}

class _PageActivitiesState extends State<PageActivities> {
  late Tree tree;

  @override
  void initState() {
    super.initState();
    tree = getTree();
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(tree.root.name),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.home),
              onPressed: () {}
            // TODO go home page = root
          ),
          //TODO other actions
          IconButton(icon: Icon(Icons.video_file_outlined),
              onPressed: () {
              Navigator.of(context)
                  .push(MaterialPageRoute<void>(
              builder: (context) => PageReport(),
              ));
              },
          ),
        ],
      ),
      body: ListView.separated(
        // it's like ListView.builder() but better
        // because it includes a separator between items
        padding: const EdgeInsets.all(16.0),
        itemCount: tree.root.children.length,
        itemBuilder: (BuildContext context, int index) =>
            _buildRow(tree.root.children[index], index),
        separatorBuilder: (BuildContext context, int index) =>
        const Divider(),
      ),
    );
  }
  Widget _buildRow(Activity activity, int index) {
    String strDuration = Duration(seconds: activity.duration).toString().split('.').first;
    // split by '.' and taking first element of resulting list
    // removes the microseconds part
    assert (activity is Project || activity is Task);
    if (activity is Project) {
      return ListTile(
        title: Text('${activity.name}'),
        trailing: Text('$strDuration'),
        onTap: () => {},
        // TODO, navigate down to show children tasks and projects
      );
    } else {
      Task task = activity as Task;
      Widget trailing;
      trailing = Text('$strDuration');
      return ListTile(
        title: Text('${activity.name}'),
        trailing: trailing,
        onTap: () => _navigateDownIntervals(index),
        onLongPress: () {}, // TODO start/stop counting the time for this task
      );
    }
  }
  void _navigateDownIntervals(int childId) {
    Navigator.of(context)
        .push(MaterialPageRoute<void>(builder: (context) => PageIntervals())
    );
  }
}
