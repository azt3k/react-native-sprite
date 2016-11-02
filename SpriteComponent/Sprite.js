// Sprite component bridged with native component (react-native 0.34)
// By William Ngan, 10/2016

import React, { Component } from 'react';
import { requireNativeComponent, NativeModules, findNodeHandle, Platform } from 'react-native';

// refers to Sprite.swift we have in XCode
const SpriteNative = requireNativeComponent('Sprite', Sprite);

// refers to SpriteManager.swift
const SpriteManager = NativeModules.SpriteManager || NativeModules.SpriteModule;

class Sprite extends Component {


  constructor(props) {
    super(props);

  }

  createSequence(nameWithPath, count, format, duration) {

    let node = findNodeHandle(this),
        args = [nameWithPath, count || 1, format || "png", duration || 0.5];

    if (Platform.OS == 'ios')
      SpriteManager.createSequence(node, ...args);

    else
      NativeModules.UIManager.dispatchViewManagerCommand(
        node,
        NativeModules.UIManager.Sprite.Commands.createSequence,
        args
      );
  }

  animate(shouldPlay) {

    let node = findNodeHandle(this),
        args = [shouldPlay || false ];

    if (Platform.OS == 'ios')
      SpriteManager.animate(findNodeHandle(this), ...args);

    else
      NativeModules.UIManager.dispatchViewManagerCommand(
        node,
        NativeModules.UIManager.Sprite.Commands.animate,
        args
      );
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.imagePath != this.props.imagePath) {
      this.createSequence( nextProps.imagePath, nextProps.count, nextProps.format, nextProps.duration );
      this.animate( nextProps.animated );
    }
  }

  // On Mount, initiate the sequence from the props
  componentDidMount() {
    this.createSequence( this.props.imagePath, this.props.count, this.props.format, this.props.duration );
    this.animate( this.props.animated );
  }


  _onLayout(evt) {
    // Handle layout changes if you need
  }


  render() {

    return <SpriteNative style={ this.props.style }
                         onLayout={this._onLayout.bind(this)}
                         repeatCount={ this.props.repeatCount || 0 }
                         duration={this.props.duration || 0.5}
                         imageNumber={this.props.imageNumber || 0}
                         imageLayout={ this.props.imageLayout || "contain"}
                         animated={this.props.animated || false} />;
  }

}

module.exports = Sprite;
