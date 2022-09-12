%% LHS play

clear all
close all
clc

V(:,1) = [20; 60];                                  % FrRH
V(:,2) = [45; 65];                                  % RrRH
V(:,3) = [0; 15];                                   % Yaw
V(:,4) = [0; 2];                                    % Roll
V(:,5) = [0; 12.5];                                 % Steer

N = 25;                                             % Number of Samples

rng default                                         % For reproducibility
% X = lhsdesign(N,5);                               % Continuous design
% X = lhsdesign(N,5,'Smooth','off');                % Discrete design
 X = lhsdesign(N,5,'Criterion','correlation');      % Correlation

S = [];

for i=1:N
    for j=1:5
      S(i,j) = X(i,j)* (V(2,j)-V(1,j)) + V(1,j);
    end
end

 