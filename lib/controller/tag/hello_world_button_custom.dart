import 'package:flutter/material.dart';

class HelloWorldButtonCustom extends StatefulWidget {
  final Widget? child;

  HelloWorldButtonCustom({
    Key? key,
    this.child,
  }) : super(key: key);

  @override
  _HelloWorldButtonCustomState createState() => _HelloWorldButtonCustomState();
}

class _HelloWorldButtonCustomState extends State<HelloWorldButtonCustom> {
  @override
  Widget build(BuildContext context) {
    return widget.child!;
  }
}
