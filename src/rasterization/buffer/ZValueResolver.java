package rasterization.buffer;

import screen.ScreenPoint;

public interface ZValueResolver {

    float resolve(ScreenPoint point);
}
